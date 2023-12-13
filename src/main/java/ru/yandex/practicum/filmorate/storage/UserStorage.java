package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    User createUser(User user); /* метод предназначен для создания нового пользователя в хранилище. Принимает объект класса `User` в качестве параметра и возвращает созданного пользователя.
     */

    User updateUser(User user); /* метод обновления информации о пользователе в хранилище. Принимает объект класса `User` с новыми данными пользователя и возвращает обновленный объект `User`.
     */

    void deleteUsers(); /* метод удаления всех пользователей из хранилища. Не возвращает результат.
     */

    User getUserById(Long id); /* метод получения пользователя по его идентификатору. Принимает идентификатор пользователя и возвращает соответствующий объект класса `User`
     */

    List<User> getUsers(); /* метод получения всех пользователей из хранилища. Возвращает список объектов класса `User`.
     */

}
