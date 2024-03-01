package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmServiceTest {
    private FilmService filmService;

    @BeforeEach
    void setUp() {
        filmService = new FilmService(new InMemoryFilmStorage(new HashMap<>()));
    }

    @Test
    void addFilm() {
        Film film = Film.builder().description("description")
                .duration(60)
                .releaseDate(LocalDate.of(2022, 02, 23))
                .build();

        assertThrows(ValidateException.class, () -> filmService.addModel(film));
        film.setName(" ");
        assertThrows(ValidateException.class, () -> filmService.addModel(film));
        film.setDescription(" wqeqwrwefsdgsdevygfghdfgsdrthdfhdfgsgsdgdsgdgfdfgdfggggggfdffhljhjlhkjlhiuhiulhjlknjhkjdsafjdfasofuawejflkasjfls;jfiasojfeiajs;fe;kasjdfksjfdiosjfisjfsidhgdfgskdjfksdjfjsif fjs kfj sdkfjas;lfd ;sjfkas");
        film.setName("film1");
        assertThrows(ValidateException.class, () -> filmService.addModel(film));
        film.setDescription("description");
        film.setReleaseDate(LocalDate.of(1895, 12, 25));
        assertThrows(ValidateException.class, () -> filmService.addModel(film));
        film.setReleaseDate(LocalDate.of(2022, 02, 23));
        film.setDuration(-1);
        assertThrows(ValidateException.class, () -> filmService.addModel(film));
    }
}