package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.StorageFilm;

import java.time.LocalDate;
import java.util.Objects;
import java.util.TreeSet;

@Service
public class FilmService extends ServiceFilm {

    //    protected static final String FILM_STORAGE = "inMemoryFilmStorage";
    protected static final String FILM_STORAGE = "FilmDBStorage";
    protected static final String FILM_NAME_BLANK_EXCEPTION = "Не заполнено название фильма";
    protected static final String FILM_DESCRIPTION_EXCEPTION = "Длинна описания привышает 200 символов";
    protected static final String FILM_DATE_PRODUCE_EXCEPTION = "Дата выпуска не может быть раньше появления самого кино";
    protected static final String FILM_DURATION_EXCEPTION = "Продолжительность не может быть меньше или ровна 0";


    public FilmService(@Qualifier(FILM_STORAGE) StorageFilm storage) {
        super(storage);
    }

    protected void validate(Film film) throws ValidateException {
        if (Objects.isNull(film.getGenres())) {
            film.setGenres(new TreeSet<>());
        }

        if (Objects.isNull(film.getLikes())) {
            film.setLikes(new TreeSet<>());
        }

        if (Objects.isNull(film.getName()) || film.getName().isBlank() || film.getName().isEmpty()) {
            throw new ValidateException(FILM_NAME_BLANK_EXCEPTION);
        }

        if (film.getDescription().length() > 200) {
            throw new ValidateException(FILM_DESCRIPTION_EXCEPTION);
        }
        final String dateStartFilmEpoch = "1895-12-28";
        if (film.getReleaseDate().isBefore(LocalDate.parse(dateStartFilmEpoch))) {
            throw new ValidateException(FILM_DATE_PRODUCE_EXCEPTION);
        }

        if (film.getDuration() <= 0) {
            throw new ValidateException(FILM_DURATION_EXCEPTION);
        }
    }

}
