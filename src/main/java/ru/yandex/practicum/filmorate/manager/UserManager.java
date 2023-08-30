package ru.yandex.practicum.filmorate.manager;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Data
public class UserManager {
    private HashMap<Integer, User> allUsers = new HashMap<>();
    private int userId;


    public User addUser(User user) {
        loginValidation(user.getLogin());
        emailValidation(user.getEmail());
        nameValidation(user.getName(), user.getLogin());
        birthdayValidation(user);
        createId(user);
        allUsers.put(userId, user);
        log.info("Пользователь создан", user.getName(), user.getId());
        return user;
    }

    public void createId(User user) {
        user.setId(++userId);
    }

    public User updateUser(User user) {
        if (allUsers.containsKey(user.getId())) {
            loginValidation(user.getLogin());
            emailValidation(user.getEmail());
            nameValidation(user.getName(), user.getLogin());
            birthdayValidation(user);
            allUsers.put(user.getId(), user);
            return user;
        } else {
            return null;
        }
    }

    public List<User> getUserList() {
        List<User> list = new ArrayList<>(allUsers.values());
        return list;
    }

    private void emailValidation(String email) throws ValidationException {
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new ValidationException("Email не должен быть пустым и должен содержать @");
        }
    }

    private void loginValidation(String login) throws ValidationException {
        if (login.isBlank() || login.isEmpty()) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
    }

    private void nameValidation(String name, String login) throws ValidationException {
        if (name == null || name.isEmpty()) {
            name = login;
            throw new ValidationException("Вместо имени будет использован логин");
        }
    }

    private void birthdayValidation(User user) throws ValidationException {
        if (user.getBirthday().isAfter(LocalDate.now()) || user.getBirthday() == null) {
            throw new ValidationException("Некорректная дата рождения" + user.getId() + "'");
        }
    }
}
