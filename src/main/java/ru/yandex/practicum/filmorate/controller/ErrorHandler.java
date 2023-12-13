package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.InternalServiceException;
import ru.yandex.practicum.filmorate.model.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class ErrorHandler { /* класс ErrorHandler, который отвечает за обработку исключений в приложении.
В нем определены несколько методов-обработчиков с аннотацией @ExceptionHandler, каждый из которых отвечает
за обработку конкретного типа исключения. */


    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final ObjectNotFoundException exception) {
        return new ErrorResponse(exception.getMessage());
    } /* обрабатывает исключение ObjectNotFoundException с помощью аннотации @ResponseStatus, которая устанавливает
    код состояния HTTP для возвращаемого ответа. В данном случае установлен код состояния 404 (NOT_FOUND), что означает,
    что запрошенный ресурс не найден. Метод возвращает объект ErrorResponse с сообщением об ошибке. */

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInternalServiceException(final InternalServiceException exception) {
        return new ErrorResponse(exception.getMessage());
    } /* обрабатывает исключение InternalServiceException и устанавливает код состояния 500 (INTERNAL_SERVER_ERROR)
    для возвращаемого ответа.
     */

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final ValidationException exception) {
        return new ErrorResponse(exception.getMessage());
    } /*обрабатывает исключение ValidationException и устанавливает код состояния 400 (BAD_REQUEST) для возвращаемого ответа.
     */
}
