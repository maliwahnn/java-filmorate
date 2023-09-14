package ru.yandex.practicum.filmorate.manager;

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

    public static void allUserExceptions(String email, String login, String name, User user) throws ValidationException {
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new ValidationException("Email не должен быть пустым и должен содержать @");
        }

        if (login.isBlank() || login.isEmpty()) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }

        if (name == null || name.isEmpty()) {
            throw new ValidationException("Вместо имени будет использован логин");
        }

        if (user.getBirthday().isAfter(LocalDate.now()) || user.getBirthday() == null) {
            throw new ValidationException("Некорректная дата рождения" + user.getId() + "'");
        }
    }
}
