package ru.yandex.practicum.filmorate.controller;


import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.manager.FilmManager;
import ru.yandex.practicum.filmorate.model.Film;


import java.util.List;

@RestController
@RequestMapping ("/films")
public class FilmController {
    FilmManager filmManager = new FilmManager();

    @GetMapping
    public List<Film> getFilm() {
        return filmManager.getFilmsList();
    }
    @PutMapping
    public Film update(@RequestBody Film film) {
        return filmManager.updateFilm(film);
    }
    @PostMapping
    public Film add(@RequestBody Film film) {
        return filmManager.addFilm(film);
    }

}