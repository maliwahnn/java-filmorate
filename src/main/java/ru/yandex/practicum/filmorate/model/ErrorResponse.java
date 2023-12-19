package ru.yandex.practicum.filmorate.model;

public class ErrorResponse {

    private final String error;

    /**
     * Конструктор, принимающий сообщение об ошибке
     */
    public ErrorResponse(String error) {
        this.error = error;
    }

    /**
     * Геттер для получения сообщения об ошибке
     */
    public String getError() {
        return error;
    }
}


