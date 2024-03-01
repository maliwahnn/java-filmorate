package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/users")
public class UserController extends Controller<User> {

    private static final String SERVICE = "UserService";

    public UserController(@Qualifier(SERVICE) UserService service) {
        super(service);
    }

    @PostMapping
    public User add(@RequestBody @Valid User model) throws ValidateException {
        return super.add(model);
    }

    @PutMapping
    public User update(@RequestBody @Valid User model) throws NotFoundException {
        return super.update(model);
    }

    @GetMapping("/{id}")
    public User get(@PathVariable int id) throws NotFoundException {
        return super.get(id);
    }

}
