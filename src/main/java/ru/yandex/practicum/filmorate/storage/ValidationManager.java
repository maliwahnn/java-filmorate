package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class ValidationManager {

    public static void allFilmExceptions(String name, String description, LocalDate date, double time) throws ValidationException {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Поле name не должно быть пустым");
        }

        if (description.length() > 200 || description.length() == 0) {
            throw new ValidationException("Максимальная длина должна быть не больше 200 символов");
        }

        if (date == null || date.isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Некорректная дата");
        }

        if (time <= 0) {
            throw new ValidationException("Неверная длительность фильма");
        }
    }
}
