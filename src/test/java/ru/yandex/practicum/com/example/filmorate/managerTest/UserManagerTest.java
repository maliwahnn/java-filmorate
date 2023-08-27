package ru.yandex.practicum.com.example.filmorate.managerTest;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.yandex.practicum.com.example.filmorate.manager.FilmManager;
import ru.yandex.practicum.com.example.filmorate.manager.UserManager;
import ru.yandex.practicum.com.example.filmorate.model.Film;
import ru.yandex.practicum.com.example.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserManagerTest {
    UserManager manager = new UserManager();
    private final User user = new User(1, "di@mail.ru", "balda", "aa",   LocalDate.of(1995, 1, 1));
    private final User  updatedUser = new User(1, "xi@mail.ru", "nefritoviy", "sterzen",   LocalDate.of(2000, 1, 1));

    @Test
    public void addUserTest() {
        manager.addUser(user);

        Assertions.assertEquals(1, manager.getAllUsers().size());
    }
    @Test
    public void updateFilmTest() {
        manager.addUser(user);
        manager.updateUser(updatedUser);

        Assertions.assertEquals("xi@mail.ru", updatedUser.getEmail());
        Assertions.assertEquals(user.getId(), updatedUser.getId());
        Assertions.assertEquals(1, manager.getAllUsers().size());
    }
    @Test
    public void testGetFilmsList() {
        manager.addUser(user);
        manager.addUser(updatedUser);
        List<User> expectedList = new ArrayList<>(manager.getAllUsers().values());
        List<User> actualList = new ArrayList<>();
        actualList.add(user);
        actualList.add(updatedUser);

        Assertions.assertEquals(expectedList, actualList);
    }
}
