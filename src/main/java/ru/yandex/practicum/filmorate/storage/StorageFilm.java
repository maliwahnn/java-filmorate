package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Model;

import java.util.List;

public interface StorageFilm extends Storage {
    <T extends Model> T getGenreById(int id);

    List<? extends Model> getGenreList();

    <T extends Model> T getMpa(int id);

    List<? extends Model> getMpaList();
}
