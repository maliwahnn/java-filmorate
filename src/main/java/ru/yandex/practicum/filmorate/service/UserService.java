package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService extends InMemoryUserStorage {
    /* Класс UserService представляет сервис для работы с данными о пользователях.
    Он наследуется от класса InMemoryUserStorage, что предполагает наличие методов для получения, добавления и удаления пользователей.
     */

    public void addFriend(Long userId, Long friendId) { /* добавляет друга пользователю.
    Он принимает идентификаторы пользователя и друга, получает объекты пользователей по их идентификаторам и добавляет друга в список друзей пользователя, а также пользователя в список друзей друга.
     */
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        user.addFriend(friendId);
        friend.addFriend(userId);
    }

    public void deleteFriend(Long userId, Long friendId) { /* удаляет друга у пользователя.
    Он также принимает идентификаторы пользователя и друга, получает объекты пользователей и удаляет друга из списка друзей пользователя и пользователя из списка друзей друга.
     */
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        user.removeFriend(friendId);
        friend.removeFriend(userId);
    }

    public List<User> getFriends(Long userId) { /* возвращает список друзей пользователя.
    Он принимает идентификатор пользователя, получает объект пользователя по его идентификатору и возвращает список объектов друзей пользователя.
     */
        User user = getUserById(userId);
        Set<Long> friends = user.getFriends();
        if (friends.isEmpty()) {
            throw new ObjectNotFoundException("Список друзей пользователя " + userId + " пуст");
        }
        return friends.stream()
                .map(this::getUserById)
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(Long userId, Long friendId) { /* возвращает список общих друзей между двумя пользователями.
    Он принимает идентификаторы пользователя и друга, получает объекты пользователей, получает списки идентификаторов друзей каждого пользователя и находит общих друзей.
    Затем метод возвращает список объектов общих друзей.
    */
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        Set<Long> userFriends = user.getFriends();
        Set<Long> friendFriends = friend.getFriends();
        Set<Long> commonFriends = new HashSet<>();

        for (Long friendUserId : userFriends) {
            if (friendFriends.contains(friendUserId)) {
                commonFriends.add(friendUserId);
            }
        }

        List<User> commonFriendUsers = new ArrayList<>();
        for (Long commonFriendId : commonFriends) {
            commonFriendUsers.add(getUserById(commonFriendId));
        }

        return commonFriendUsers;
    }
}
