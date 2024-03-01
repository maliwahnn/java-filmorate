package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.yandex.practicum.filmorate.model.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Objects;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmDBStorageTest {

    private final FilmDBStorage storageFilm;
    private final UserDBStorage userDBStorage;
    TreeSet<Genre> genreTreeSet = new TreeSet<>();
    TreeSet<Integer> likes = new TreeSet<>();

    User user = User.builder().name("user").email("user@mail.ru").login("userLogin").birthday(LocalDate.of(1993, 2, 12)).build();
    Film film = Film.builder().name("name").genres(genreTreeSet).description("description")
            .releaseDate(LocalDate.of(2021, 3, 23)).duration(45).mpa(new Mpa(1))
            .likes(likes).build();
    Film film2 = Film.builder().name("name2").genres(genreTreeSet).description("description2")
            .releaseDate(LocalDate.of(2021, 3, 23)).duration(45).mpa(new Mpa(1))
            .likes(likes).build();


    @Test
    public void save() {
        Collections.addAll(genreTreeSet, new Genre(1, null), new Genre(3, null));
        Collections.addAll(likes, 1);
        userDBStorage.save(user);
        storageFilm.save(film);
        storageFilm.save(film2);

        Film filmGet = storageFilm.get(1);

        assertEquals(1, filmGet.getId());
        assertEquals(2, storageFilm.get(2).getId());
        storageFilm.delete(1);
        assertThrows(EmptyResultDataAccessException.class, () -> storageFilm.get(1));
        assertTrue(storageFilm.get(2).getLikes().stream().anyMatch(e -> Objects.equals(e, 1)));
        storageFilm.removeIdFromIdSet(new FilmLikes(2, 1));
        assertEquals(0, storageFilm.get(2).getLikes().size());

    }

    @Test
    public void update() {
        storageFilm.save(film);

        Film updateWithName = Film.builder().name("nameUpdate").genres(genreTreeSet).description("descriptionUpdate")
                .releaseDate(LocalDate.of(2021, 3, 23)).duration(45).mpa(new Mpa(1))
                .likes(likes).id(1).build();

        storageFilm.update(updateWithName);

        Film updateGet = storageFilm.get(1);

        assertEquals(updateGet.getName(), "nameUpdate");

        Collections.addAll(genreTreeSet, new Genre(2, null));

        storageFilm.update(updateWithName);
        assertTrue(storageFilm.get(1).getGenres().stream().anyMatch(e -> Objects.equals(e.getId(), 2)));
    }

    @Test
    void getMpaGenres() {
        assertEquals(1, storageFilm.getMpa(1).getId());
        assertEquals(5, storageFilm.getMpaList().size());
        assertEquals(1, storageFilm.getGenreById(1).getId());
        assertEquals(6, storageFilm.getGenreList().size());

    }
}