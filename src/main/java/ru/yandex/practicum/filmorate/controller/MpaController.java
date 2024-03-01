package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.ServiceFilm;

import java.util.List;

@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MpaController {
    private final ServiceFilm filmService;

    @GetMapping
    public List<Mpa> getMpaList() {
        return filmService.getMpaList();
    }

    @GetMapping("/{id}")
    public Mpa getMpaById(@PathVariable int id) throws NotFoundException {
        return filmService.getMpa(id);
    }

}
