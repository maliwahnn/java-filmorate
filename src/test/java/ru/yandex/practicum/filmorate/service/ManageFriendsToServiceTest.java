package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.FriendsTo;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.StorageUser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class ManageFriendsToServiceTest {
    private final StorageUser userStorage = new InMemoryUserStorage(new HashMap<>());
    private final UserService userService = new UserService(userStorage);
    private final ManageFriendsUserService manageFriendsUserService = new ManageFriendsUserService(userStorage);

    @Test
    public void addFriend() throws ValidateException, NotFoundException {
        User user = User.builder()
                .birthday(LocalDate.of(1994, 6, 12))
                .email("my@email.ru")
                .login("vasyaPupkin")
                .friends(new ArrayList<>())
                .build();

        User user1 = User.builder()
                .birthday(LocalDate.of(1994, 6, 12))
                .email("123my@email.ru")
                .login("vasislisaPupkina")
                .friends(new ArrayList<>())
                .build();

        User user3 = User.builder()
                .birthday(LocalDate.of(1994, 6, 12))
                .email("123@email.ru")
                .login("vasisPupkin")
                .friends(new ArrayList<>())
                .build();

        manageFriendsUserService.addToFriends(new FriendsTo(userService.addModel(user3).getId(), userService.addModel(user).getId()));
        manageFriendsUserService.addToFriends(new FriendsTo(userService.addModel(user1).getId(), user3.getId()));


        manageFriendsUserService.addToFriends(new FriendsTo(userService.addModel(user1).getId(), userService.addModel(user).getId()));
        System.out.println(user1.getFriends());
        System.out.println(manageFriendsUserService.getCommonFriends(new FriendsTo(user.getId(), user1.getId())));
    }
}
