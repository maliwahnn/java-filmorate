package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.ServiceFilm;

import java.util.List;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenresController {
    private final ServiceFilm filmService;

    @GetMapping("/{id}")
    public Genre getGenreByID(@PathVariable int id) throws NotFoundException {
        return filmService.getGenre(id);
    }

    @GetMapping
    public List<Genre> getGenreList() {
        return filmService.getGenreList();
    }

}
