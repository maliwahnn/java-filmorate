package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.FromTo;
import ru.yandex.practicum.filmorate.model.Model;

import java.util.Map;

public interface Storage {

    boolean isExist(int id);

    void update(Model model);

    Model save(Model model);

    <T extends Model> T get(int id);

    void delete(int id);

    Map<Integer, ?> getModelMap();

    void removeIdFromIdSet(FromTo user);

    <T extends Model> T addToSet(FromTo filmLikes);


}
