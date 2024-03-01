package ru.yandex.practicum.filmorate.service;

import org.springframework.dao.EmptyResultDataAccessException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.StorageFilm;

import java.util.List;

public abstract class ServiceFilm extends Service<Film> {
    private static StorageFilm storageFilm;

    public ServiceFilm(StorageFilm storage) {
        super(storage);
        storageFilm = storage;
    }

    public <T extends Model> T getGenre(int id) throws NotFoundException {
        try {
            return storageFilm.getGenreById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Genre не найден");
        }
    }

    public List<Genre> getGenreList() {
        return (List<Genre>) storageFilm.getGenreList();
    }

    public <T extends Model> T getMpa(int id) throws NotFoundException {
        try {
            return storageFilm.getMpa(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("mpa не айден");
        }
    }

    public List<Mpa> getMpaList() {
        return (List<Mpa>) storageFilm.getMpaList();
    }
}
