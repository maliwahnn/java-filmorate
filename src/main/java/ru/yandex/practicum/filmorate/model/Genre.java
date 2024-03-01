package ru.yandex.practicum.filmorate.model;


public class Genre extends Model implements Comparable<Genre> {
    public Genre(int id, String name) {
        this.setId(id);
        this.setName(name);
    }

    @Override
    public int compareTo(Genre o) {
        return this.getId() - o.id;
    }
}
