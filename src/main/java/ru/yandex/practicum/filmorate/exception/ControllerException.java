package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.model.ExceptionEntity;

@RestControllerAdvice
public class ControllerException {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionEntity handlerValidateException(final ValidateException validateException) {
        return new ExceptionEntity("validateException", validateException.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionEntity handlerNotFoundException(final NotFoundException notFoundException) {
        return new ExceptionEntity("not found exception", notFoundException.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionEntity handlerException(final Exception exception) {
        return new ExceptionEntity("exception", exception.getMessage());
    }
}
