package ru.yandex.practicum.filmorate.model;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
public class Film {
    /**
     * Класс  представляет объект фильма и содержит различные свойства и методы для работы с ними
     */
    @PositiveOrZero
    private Long id;
    /**
     * Идентификатор фильма
     */
    @NotNull
    private String name;
    /**
     * Название фильма
     */
    @Size(min = 1, max = 200)
    private String description;
    /**
     * Описание фильма
     */
    private LocalDate releaseDate;
    /**
     * Дата выхода фильма
     */
    @Positive
    private long duration;
    /**
     * Продолжительность фильма в минутах
     */
    private Set<Long> likes;
    /**
     * Множество идентификаторов пользователей, поставивших лайки фильму
     */

    public void addLike(Long userId) {    // Метод для добавления лайка от пользователя к фильму
        likes.add(userId);
    }

    public void removeLike(Long userId) { // Метод для удаления лайка от пользователя к фильму
        likes.remove(userId);
    }

    public int getLikesQuantity() {       // Метод для получения количества лайков у фильма
        return likes.size();
    }
}

