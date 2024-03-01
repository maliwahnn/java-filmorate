package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public abstract class Model {
    protected int id;
    protected String name;
}
