package ru.yandex.practicum.filmorate.manager;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Slf4j
@Data
public class FilmManager {
    private HashMap<Integer, Film> allFilms = new HashMap<>();
    private int filmId;

    public Film addFilm(Film film) {
        descriptionValidation(film.getDescription());
        dateValidation(film.getReleaseDate());
        timeValidation(film.getDuration());
        nameValidation(film.getName());
        createId();
        allFilms.put(filmId, film);
        log.info("Фильм создан", film.getName(), film.getId());
        return film;
    }
    public int createId() {
        return ++filmId;
    }

    public Film updateFilm(Film film) {
        descriptionValidation(film.getDescription());
        dateValidation(film.getReleaseDate());
        timeValidation(film.getDuration());
        nameValidation(film.getName());
        allFilms.put(film.getId(),film);
        return film;
    }

    public List<Film> getFilmsList() {
        List<Film> list = new ArrayList<>(allFilms.values());
        return list;
    }
    private void nameValidation(String name) throws ValidationException {
        if (name == null || name.isBlank())  {
            throw new ValidationException("Поле name не должно быть пустым");
        }
    }
    private void descriptionValidation(String description) throws ValidationException  {
        if (description.length() > 200 || description.length() == 0) {
            throw new ValidationException("Максимальная длина должна быть не больше 200 символов");
        }
    }
    private void dateValidation (LocalDate date) throws ValidationException {
        if (date == null || date.isBefore(LocalDate.of(1895,12,28))) {
            throw new ValidationException("Некорректная дата");
        }
    }
    private void timeValidation (double time) throws ValidationException {
        if (time <= 0) {
            throw new ValidationException("Неверная длительность фильма");
        }
    }


}
