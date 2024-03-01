package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.FromTo;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;

@Component("inMemoryUserStorage")
@RequiredArgsConstructor
public class InMemoryUserStorage implements StorageUser {
    private final Map<Integer, User> userMap;
    private Integer id = 0;

    public boolean isExist(int id) {
        return userMap.containsKey(id);
    }

    public void update(Model model) {
        userMap.put(model.getId(), (User) model);
    }

    public Model save(Model model) {
        id++;
        model.setId(id);
        userMap.put(id, (User) model);

        return model;
    }

    public User get(int id) {
        return userMap.get(id);
    }

    public void delete(int id) {
        userMap.remove(id);
    }

    @Override
    public Map<Integer, User> getModelMap() {
        return userMap;
    }

    public void removeIdFromIdSet(FromTo user) {
        User user1 = userMap.get(user.getFrom());
        User user2 = userMap.get(user.getTo());
        List<Integer> userFr1 = user1.getFriends();
        List<Integer> userFr2 = user2.getFriends();

        userFr1.remove(user2.getId());
        userFr2.remove(user1.getId());

    }

    public User addToSet(FromTo user) {
        User user1 = userMap.get(user.getFrom());
        User user2 = userMap.get(user.getTo());
        List<Integer> userFr1 = user1.getFriends();
        List<Integer> userFr2 = user2.getFriends();

        userFr1.add(user2.getId());
        userFr2.add(user1.getId());

        return user1;
    }

}
