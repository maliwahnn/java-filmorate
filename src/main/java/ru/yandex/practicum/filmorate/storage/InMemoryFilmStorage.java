package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FromTo;
import ru.yandex.practicum.filmorate.model.Model;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component("inMemoryFilmStorage")
@RequiredArgsConstructor
public class InMemoryFilmStorage implements StorageFilm {
    protected final Map<Integer, Film> filmMap;
    private Integer id = 0;

    public boolean isExist(int id) {
        return filmMap.containsKey(id);
    }

    public void update(Model model) {
        filmMap.put(model.getId(), (Film) model);
    }

    public Model save(Model model) {
        id++;
        model.setId(id);
        filmMap.put(id, (Film) model);
        return model;
    }

    public Film get(int id) {
        return filmMap.get(id);
    }

    public void delete(int id) {
        filmMap.remove(id);
    }

    public Map<Integer, Film> getModelMap() {
        return filmMap;
    }

    public void removeIdFromIdSet(FromTo films) {
    }

    @Override
    public <T extends Model> T getGenreById(int id) {
        return null;
    }

    @Override
    public List<? extends Model> getGenreList() {
        return null;
    }

    @Override
    public <T extends Model> T getMpa(int id) {
        return null;
    }

    @Override
    public List<? extends Model> getMpaList() {
        return null;
    }

    @Override
    public Film addToSet(FromTo filmLikes) {
        Film film = filmMap.get(filmLikes.getFrom());
        Set<Integer> likes = film.getLikes();

        likes.add(filmLikes.getTo());
        return film;
    }

}
