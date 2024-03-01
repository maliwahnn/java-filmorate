package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.FriendsTo;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.ManageFriendsUserService;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@RestController
public class UserLikeController extends UserController {

    private static final String SERVICE = "ManageFriendsUserService";
    private final ManageFriendsUserService manageFriendsUserService;

    public UserLikeController(@Qualifier(SERVICE) UserService filmService) {
        super(filmService);
        manageFriendsUserService = (ManageFriendsUserService) filmService;
    }

    @PutMapping("/{idUser}/friends/{idFriend}")
    public void addToFriends(@PathVariable int idUser, @PathVariable int idFriend) throws NotFoundException {
        manageFriendsUserService.addToFriends(new FriendsTo(idUser, idFriend));
    }

    @DeleteMapping("/{idUser}/friends/{idFriend}")
    public void deleteFromFriends(@PathVariable int idUser, @PathVariable int idFriend) {
        manageFriendsUserService.removeFriends(new FriendsTo(idUser, idFriend));
    }

    @GetMapping("/{idUser}/friends")
    public List<User> getFriend(@PathVariable Integer idUser) throws NotFoundException {
        return manageFriendsUserService.getFriends(idUser);
    }

    @GetMapping("/{idUser}/friends/common/{idFriend}")
    public List<User> getCommonFriend(@PathVariable int idUser, @PathVariable int idFriend) throws NotFoundException {
        return manageFriendsUserService.getCommonFriends(new FriendsTo(idUser, idFriend));
    }

}
