package ru.yandex.practicum.filmorate.storage;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.*;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository("FilmDBStorage")
@AllArgsConstructor
public class FilmDBStorage implements StorageFilm {
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean isExist(int id) {
        return get(id) != null;
    }

    @Override
    public void update(Model model) {
        Film film = (Film) model;
        String sql = "UPDATE film SET title = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? WHERE film_id = ?";

        jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getMpa().getId(), film.getId());
        updateGenres(film);
        updateLikes(film);
    }

    @Override
    public Model save(Model model) {
        Film film = (Film) model;
        String sql = "INSERT INTO film  (title, description, release_date, duration, mpa_id)  VALUES (?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();


        jdbcTemplate.update(connection -> {
            PreparedStatement stm = connection.prepareStatement(sql, new String[]{"film_id"});
            stm.setString(1, film.getName());
            stm.setString(2, film.getDescription());
            stm.setDate(3, Date.valueOf(film.getReleaseDate()));
            stm.setInt(4, film.getDuration());
            stm.setInt(5, film.getMpa().getId());

            return stm;
        }, keyHolder);
        film.setId(keyHolder.getKey().intValue());
        saveGenres(film);
        updateLikes(film);


        return model;
    }

    private void saveGenres(Film film) {

        String sqlGenre = "INSERT INTO genre_film (genre_id, film_id) VALUES (?,?)";
        Iterator<Genre> iterator = film.getGenres().iterator();

        jdbcTemplate.batchUpdate(sqlGenre, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(2, film.getId());
                ps.setInt(1, iterator.next().getId());
            }

            @Override
            public int getBatchSize() {
                return film.getGenres().size();
            }
        });
    }

    private void updateLikes(Film film) {
        String sqlDelGenre = "DELETE FROM likes_film WHERE film_id = ?";

        jdbcTemplate.update(sqlDelGenre, film.getId());

        String sqlGenre = "INSERT INTO likes_film (user_id, film_id) VALUES (?,?)";
        Iterator<Integer> iterator = film.getLikes().iterator();
        jdbcTemplate.batchUpdate(sqlGenre, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(2, film.getId());
                ps.setInt(1, iterator.next());
            }

            @Override
            public int getBatchSize() {
                return film.getLikes().size();
            }
        });
    }

    private void updateGenres(Film film) {
        String sqlDelGenre = "DELETE FROM genre_film WHERE film_id = ?";

        jdbcTemplate.update(sqlDelGenre, film.getId());

        String sqlGenre = "INSERT INTO genre_film (genre_id, film_id) VALUES (?,?)";
        Iterator<Genre> iterator = film.getGenres().iterator();

        jdbcTemplate.batchUpdate(sqlGenre, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(2, film.getId());
                ps.setInt(1, iterator.next().getId());
            }

            @Override
            public int getBatchSize() {
                return film.getGenres().size();
            }
        });
    }

    @Override
    public Film get(int id) {
        String sqlNew = "SELECT  f.film_id, f.title, f.description, f.release_date, f.duration, f.mpa_id, g.genre_id, g.name, lf.user_id\n" +
                "FROM genre as g \n" +
                "left JOIN genre_film as gf on gf.genre_id = g.genre_id \n" +
                "right JOIN film AS f ON f.film_id = gf.film_id \n" +
                "left JOIN likes_film AS lf ON lf.film_id = f.film_id \n" +
                "WHERE f.film_id = ?";
        Film film = jdbcTemplate.queryForObject(sqlNew, this::mapRowToFilm, id);

        return film;
    }

    private Film mapRowToFilm(ResultSet rs, int rowNum) throws SQLException {
        TreeSet<Genre> filmGenre = new TreeSet<>();
        TreeSet<Integer> likes = new TreeSet<>();

        Film film = Film.builder().id(rs.getInt("film_id"))
                .name(rs.getString("title"))
                .description(rs.getString("description"))
                .genres(filmGenre)
                .duration(rs.getInt("duration"))
                .releaseDate(rs.getDate("release_date").toLocalDate())
                .mpa(getMpa(rs.getInt("mpa_id")))
                .likes(likes).build();

        do {
            if (rs.getInt("genre_id") != 0) {
                filmGenre.add(new Genre(rs.getInt("genre_id"), rs.getString("name")));
            }

            if (rs.getInt("user_id") != 0) {
                likes.add(rs.getInt("user_id"));
            }

        } while (rs.next());

        return film;
    }


    @Override
    public void delete(int id) {
        String sql = "DELETE FROM film WHERE film_id = ?";

        jdbcTemplate.update(sql, id);
    }

    @Override
    public Map<Integer, Film> getModelMap() {
        String sql1 = "SELECT film_id FROM FILM";
        Map<Integer, Film> filmMap = new HashMap<>();
        List<Integer> idsList = jdbcTemplate.query(sql1, (rs, nm) -> rs.getInt("film_id"));

        for (int id : idsList) {
            filmMap.put(id, get(id));
        }

        return filmMap;
    }

    @Override
    public void removeIdFromIdSet(FromTo user) {
        String sql = "DELETE FROM likes_film WHERE film_id = ? AND user_id = ?";

        jdbcTemplate.update(sql, user.getFrom(), user.getTo());

    }

    @Override
    public <T extends Model> T addToSet(FromTo filmLikes) {
        String sql = "INSERT INTO  likes_film (film_id, user_id) VALUES (?,?)";

        jdbcTemplate.update(sql, filmLikes.getFrom(), filmLikes.getTo());

        return null;
    }

    @Override
    public Genre getGenreById(int id) {
        String sql = "SELECT * FROM genre WHERE genre_id = ?";

        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new Genre(rs.getInt("genre_id"), rs.getString("name")), id);
    }

    @Override
    public List<Genre> getGenreList() {
        String sql = "SELECT * FROM genre ";

        return jdbcTemplate.query(sql,
                ((rs, rowNum) -> new Genre(rs.getInt("genre_id"), rs.getString("name"))));
    }

    @Override
    public Mpa getMpa(int id) {
        String sql = "SELECT * FROM MPA WHERE mpa_id = ?";

        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new Mpa(rs.getInt("mpa_id"), rs.getString("name")), id);
    }

    @Override
    public List<? extends Model> getMpaList() {
        String sql = "SELECT * FROM  MPA";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new Mpa(rs.getInt("mpa_id"),
                rs.getString("name")));
    }
}
