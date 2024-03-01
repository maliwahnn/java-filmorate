package ru.yandex.practicum.filmorate.storage;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.FromTo;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("userDBStorage")
@AllArgsConstructor
public class UserDBStorage implements StorageUser {
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean isExist(int id) {
        return get(id) != null;
    }

    @Override
    public void update(Model model) {
        User user = (User) model;
        String sql = "UPDATE users SET name = ?, birth_date = ?, email = ?, login = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, user.getName(), user.getBirthday(), user.getEmail(), user.getLogin(), user.getId());
    }

    @Override
    public User save(Model model) {
        User user = (User) model;
        String sql = ("INSERT INTO users (name, birth_date, email, login) VALUES (?,?,?,?)");
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement stmt = con.prepareStatement(sql, new String[]{"user_id"});
            stmt.setString(1, user.getName());
            stmt.setDate(2, Date.valueOf(user.getBirthday()));
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getLogin());
            return stmt;
        }, keyHolder);
        user.setId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public User get(int id) {
        String sqlUserLikes = "SELECT u.user_id, u.name, u.email, u.login, u.birth_date, fr.friend_id\n" +
                "FROM users as u\n" +
                "LEFT JOIN friend as fr ON fr.user_id = u.user_id\n" +
                "WHERE u.user_id = ?\n";
        ;
        User user = jdbcTemplate.queryForObject(sqlUserLikes, this::rowMapperUser, id);

        return user;

    }

    private Integer mapRowToInteger(ResultSet rs, int rowNum) throws SQLException {
        return rs.getInt("friend_id");
    }

    private User rowMapperUser(ResultSet rs, int idRow) throws SQLException {
        List<Integer> likes = new ArrayList<>();
        User user = User.builder().id(rs.getInt("user_id"))
                .name(rs.getString("name"))
                .email(rs.getString("email"))
                .login(rs.getString("login"))
                .birthday(rs.getDate("birth_date").toLocalDate())
                .friends(likes).build();
        do {
            if (rs.getInt("friend_id") != 0) {
                likes.add(rs.getInt("friend_id"));
            }
        } while (rs.next());
        return user;
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        jdbcTemplate.update(sql, id);

    }

    @Override
    public Map<Integer, User> getModelMap() {
        String sql = "SELECT user_id FROM users";
        Map<Integer, User> mapUsers = new HashMap<>();
        List<Integer> usersIdList = jdbcTemplate.query(sql, (rs, r) -> rs.getInt("user_id"));

        for (int id : usersIdList) {
            mapUsers.put(id, get(id));
        }

        return mapUsers;
    }

    @Override
    public void removeIdFromIdSet(FromTo user) {
        String sql = "DELETE FROM friend WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sql, user.getFrom(), user.getTo());
    }

    @Override
    public Model addToSet(FromTo userFriend) {
        String sql = "INSERT INTO  friend (user_id, friend_id, IS_APPROVED) VALUES (?,?,?);";
        jdbcTemplate.update(sql, userFriend.getFrom(), userFriend.getTo(), true);
        return null;
    }

}
