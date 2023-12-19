package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidationException extends IllegalArgumentException {
    /**
     * Класс используется для представления ошибок валидации данных
     */
    public ValidationException(final String message) {
        super(message);
        log.error(message);
    }
}
