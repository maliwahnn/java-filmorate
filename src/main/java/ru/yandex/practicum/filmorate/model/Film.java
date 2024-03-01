package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.TreeSet;

@Data
@Builder
public class Film extends Model {
    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    @Positive
    private int duration;
    private Mpa mpa;
    private TreeSet<Genre> genres = new TreeSet<>();
    private TreeSet<Integer> likes = new TreeSet<>();
}
