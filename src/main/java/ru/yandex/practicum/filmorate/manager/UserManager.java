package ru.yandex.practicum.filmorate.manager;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


import ru.yandex.practicum.filmorate.model.User;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Data
public class UserManager {
    private HashMap<Integer, User> allUsers = new HashMap<>();
    private int userId;


    public User addUser(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        ValidationManager.allUserExceptions(user.getEmail(), user.getLogin(), user.getName(), user);
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
            ValidationManager.allUserExceptions(user.getEmail(), user.getLogin(), user.getName(), user);
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
}
