package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceTest {
    private Service userService;

    @BeforeEach
    void setUp() {
        userService = new UserService((new InMemoryUserStorage(new HashMap<>())));
    }

    @Test
    void addUser() throws ValidateException {
        User user = User.builder()
                .email("my@email.ru")
                .login("vasya pupkin")
                .birthday(LocalDate.of(1994, 6, 12))
                .build();

        assertThrows(ValidateException.class, () -> userService.addModel(user));
        user.setLogin("vasyaPupkin");
        userService.addModel(user);
        assertEquals(user.getLogin(), user.getName());
        user.setBirthday(LocalDate.of(2024, 2, 23));
        assertThrows(ValidateException.class, () -> userService.addModel(user));
    }
}