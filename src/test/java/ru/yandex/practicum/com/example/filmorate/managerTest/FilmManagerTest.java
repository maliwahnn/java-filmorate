package ru.yandex.practicum.com.example.filmorate.managerTest;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.yandex.practicum.com.example.filmorate.manager.FilmManager;
import ru.yandex.practicum.com.example.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class FilmManagerTest {
    FilmManager manager = new FilmManager();
    private final Film film = new Film(1, "Film", "Описание фильма",
            LocalDate.of(1995, 2, 2), 120);
    private final Film updatedFilm = new Film(2, "Drill",
            "Drill этого фильма",
            LocalDate.of(2000, 3, 1), 180);

    @Test
   public void addFilmTest() {
        manager.addFilm(film);

        Assertions.assertEquals(1, manager.getAllFilms().size());
    }
    @Test
    public void updateFilmTest() {
        manager.addFilm(film);
        manager.updateFilm(updatedFilm);

        Assertions.assertEquals("Drill этого фильма", updatedFilm.getDescription());
        Assertions.assertEquals(2, manager.getAllFilms().size());
    }
    @Test
    public void testGetFilmsList() {
        manager.addFilm(film);
        manager.addFilm(updatedFilm);
        List<Film> expectedList = new ArrayList<>(manager.getAllFilms().values());
        List<Film> actualList = new ArrayList<>();
        actualList.add(film);
        actualList.add(updatedFilm);

        Assertions.assertEquals(expectedList, actualList);
    }
}



