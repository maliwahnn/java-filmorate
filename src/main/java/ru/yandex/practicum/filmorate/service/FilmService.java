package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService extends InMemoryFilmStorage {
    /*Этот класс представляет сервис для работы с данными фильмов. В нем используется аннотация @Slf4j, которая добавляет логгирование с использованием библиотеки SLF4J.
    Также класс помечен аннотацией @Service, что указывает, что он является компонентом сервиса.
    */

    private final UserService userService;

    /* метод для добавления лайка к фильму от пользователя. Он принимает идентификаторы фильма (`filmId`) и пользователя (`userId`),
    проверяет существование фильма и пользователя, а затем вызывает метод `addLike` у объекта фильма для добавления лайка. */
    public void addLike(Long filmId, Long userId) {
        Film film = getFilmById(filmId);
        userService.getUserById(userId);
        if (film == null) {
            throw new ObjectNotFoundException("Фильма с таким id не существует" + filmId);
        }
        film.addLike(userId);
        log.info("поставлен лайк", userId, filmId);
    }

    /* метод для удаления лайка от пользователя к фильму. Аналогичен методу `addLike`,
    но вызывает метод `removeLike` у объекта фильма для удаления лайка.
    */
    public void deleteLike(Long filmId, Long userId) {
        Film film = getFilmById(filmId);
        userService.getUserById(userId);
        if (film == null) {
            throw new ObjectNotFoundException("Фильма с таким id не существует" + filmId);
        }
        film.removeLike(userId);
        log.info("лайк удалён", userId, filmId);
    }

    /* метод для получения списка популярных фильмов. Он сортирует список фильмов по количеству лайков в обратном порядке
    и возвращает заданное количество самых популярных фильмов.
    */
    public List<Film> getPopularFilms(int count) {
        return getFilms().stream()
                .sorted(Comparator.comparingInt(Film::getLikesQuantity).reversed())
                .limit(count).collect(Collectors.toList());
    }
}

