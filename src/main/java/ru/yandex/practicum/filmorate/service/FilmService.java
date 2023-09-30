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
    //Этот класс представляет сервис для работы с данными фильмов.

    private final UserService userService;

    // Метод для добавления лайка к фильму от пользователя
    public void addLike(Long filmId, Long userId) {
        Film film = getFilmById(filmId);
        userService.getUserById(userId);
        if (film == null) {
            throw new ObjectNotFoundException("Фильма с таким id не существует" + filmId);
        }
        film.addLike(userId);
        log.info("поставлен лайк", userId, filmId);
    }

    // Метод для удаления лайка от пользователя к фильму
    public void deleteLike(Long filmId, Long userId) {
        Film film = getFilmById(filmId);
        userService.getUserById(userId);
        if (film == null) {
            throw new ObjectNotFoundException("Фильма с таким id не существует" + filmId);
        }
        film.removeLike(userId);
        log.info("лайк удалён", userId, filmId);
    }

    // Метод для получения списка популярных фильмов
    public List<Film> getPopularFilms(int count) {
        return getFilms().stream()
                .sorted(Comparator.comparingInt(Film::getLikesQuantity).reversed())
                .limit(count).collect(Collectors.toList());
    }
}

