package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films;
    private Long id;

    public InMemoryFilmStorage() {
        films = new HashMap<>();
        id = 0L;
    }

    @Override
    public Film createFilm(Film film) {
        ValidationManager.allFilmExceptions(film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration());
        films.put(film.getId(), film);
        log.info("фильм создан", film.getName(), film.getId());
        return film;
    }

    @Override
    public void deleteFilms() {
        films.clear();
    }

    @Override
    public Film updateFilm(Film film) {
        if (films.containsKey(film.getId())) {
            ValidationManager.allFilmExceptions(film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration());
            films.put(film.getId(), film);
            log.info("фильм обновлён", film.getName(), film.getId());
            return film;
        } else {
            throw new ObjectNotFoundException("Такого фильма не существует");
        }
    }

    @Override
    public Film getFilmById(Long id) {
        if (!films.containsKey(id)) {
            throw new ObjectNotFoundException("Фильма с таким id не существует" + id + "'");
        }
        return films.get(id);
    }

    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }


}
