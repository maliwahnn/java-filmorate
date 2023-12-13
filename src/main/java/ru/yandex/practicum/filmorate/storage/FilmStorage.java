package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    Film createFilm(Film film); /* метод предназначен для создания нового фильма в хранилище. Принимает объект класса `Film` в качестве параметра и возвращает созданный фильм.

     */

    void deleteFilms(); /* метод удаления всех фильмов из хранилища. Не возвращает результат.
     */

    Film getFilmById(Long id); /* метод получения фильма по его идентификатору. Принимает идентификатор фильма и возвращает соответствующий объект класса `Film`
     */

    Film updateFilm(Film film); /* метод обновления информации о фильме в хранилище. Принимает объект класса `Film` с новыми данными фильма и возвращает обновленный объект `Film`.
     */

    List<Film> getFilms(); /* метод получения всех фильмов из хранилища. Возвращает список объектов класса `Film`.
     */

}
