package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InternalServiceException extends RuntimeException { // Класс  представляет собой
    // пользовательское исключение, которое может возникать в случае проблем с внутренней службой или ошибок выполнения операций.

    public InternalServiceException(final String message) {
        super(message);
    }
}
