package ru.yandex.practicum.filmorate.manager;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


import ru.yandex.practicum.filmorate.model.Film;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Slf4j
@Data
public class FilmManager {
    private HashMap<Integer, Film> allFilms = new HashMap<>();
    private int filmId;


    public Film addFilm(Film film) {
        ValidationManager.allFilmExceptions(film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration());
        createId(film);
        allFilms.put(filmId, film);
        log.info("Фильм создан", film.getName(), film.getId());
        return film;
    }

    public void createId(Film film) {
        film.setId(++filmId);
    }

    public Film updateFilm(Film film) {
        if (allFilms.containsKey(film.getId())) {
            ValidationManager.allFilmExceptions(film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration());
            allFilms.put(film.getId(), film);
            return film;
        } else {
           return null;
        }
    }

    public List<Film> getFilmsList() {
        List<Film> list = new ArrayList<>(allFilms.values());
        return list;
    }

}
