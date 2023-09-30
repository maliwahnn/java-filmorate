package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users;
    private Long id;

    public InMemoryUserStorage() {
        users = new HashMap<>();
        id = 0L;
    }

    @Override
    public User createUser(User user) {
        ValidationManager.allUserExceptions(user.getEmail(), user.getLogin(), user.getName(), user);
        users.put(user.getId(), user);
        log.info("Пользователь создан", user.getEmail(), user.getId());
        return user;
    }

    @Override
    public User getUserById(Long id) {
        if (!users.containsKey(id)) {
            throw new ObjectNotFoundException("Пользователя с таким id не существует " + id + "'");
        }
        return users.get(id);
    }

    @Override
    public User updateUser(User user) {
        if (users.containsKey(user.getId())) {
            ValidationManager.allUserExceptions(user.getEmail(), user.getLogin(), user.getName(), user);
            users.put(user.getId(), user);
            log.info("Пользователь обновлён", user.getLogin(), user.getId());
            return user;
        } else {
            throw new ObjectNotFoundException("Пользователя не существует");
        }
    }

    @Override
    public void deleteUsers() {
        users.clear();
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }


    }
