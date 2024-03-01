package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmLikes;
import ru.yandex.practicum.filmorate.service.ManageLikeFilmService;
import ru.yandex.practicum.filmorate.service.ServiceFilm;

import java.util.List;

@RestController
public class FilmLikeController extends FilmController {
    protected static final String USE_SERVICE = "ManageLikeFilmService";
    private final ManageLikeFilmService manageLikeFilmService;

    public FilmLikeController(@Qualifier(USE_SERVICE) ServiceFilm filmService) {
        super(filmService);
        manageLikeFilmService = (ManageLikeFilmService) filmService;
    }

    @PutMapping("/{id}/like/{userId}")
    public void setLike(@PathVariable int id, @PathVariable int userId) {
        manageLikeFilmService.addLike(new FilmLikes(id, userId));
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) throws NotFoundException {
        manageLikeFilmService.deleteLike(new FilmLikes(id, userId));
    }

    @GetMapping("/popular")
    public List<Film> getFilmsPopular(@RequestParam(defaultValue = "10") int count) {

        return manageLikeFilmService.getFilmsPopular(count);
    }
}
