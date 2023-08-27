package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.manager.UserManager;
import ru.yandex.practicum.filmorate.model.User;


import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    UserManager userManager = new UserManager();

    @GetMapping
    public List<User> getFilm() {
        return userManager.getUserList();
    }
    @PutMapping
    public User update(@RequestBody User user) {
        return userManager.updateUser(user);
    }
    @PostMapping
    public User add(@RequestBody User user) {
        return userManager.addUser(user);
    }

}
