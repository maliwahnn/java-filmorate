package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmLikes;
import ru.yandex.practicum.filmorate.storage.StorageFilm;
import ru.yandex.practicum.filmorate.storage.StorageUser;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("ManageLikeFilmService")
public class ManageLikeFilmService extends FilmService {
    private final StorageUser storageUser;

    public ManageLikeFilmService(@Qualifier(FilmService.FILM_STORAGE) StorageFilm storage,
                                 @Qualifier(UserService.USER_STORAGE) StorageUser storageUser) {
        super(storage);
        this.storageUser = storageUser;
    }

    public Film addLike(FilmLikes filmLikes) {
        return storage.addToSet(filmLikes);
    }

    public Film deleteLike(FilmLikes filmLikes) throws NotFoundException {
        validateExistUser(filmLikes.getTo());

        storage.removeIdFromIdSet(filmLikes);

        Film film = (Film) getModelById(filmLikes.getFrom());

        return film;
    }

    public Set<Integer> getLikes(Integer idFilm) throws NotFoundException {
        Film film = (Film) getModelById(idFilm);
        Set<Integer> likes = film.getLikes();

        return likes;
    }

    public List<Film> getFilmsPopular(int count) {
        List<Film> filmList = super.getModelList();

        return filmList.stream().sorted((e1, e2) -> e2.getLikes().size() - e1.getLikes().size())
                .limit(count).collect(Collectors.toList());
    }

    private boolean validateExistUser(int userId) throws NotFoundException {
        try {
            storageUser.isExist(userId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(String.format(UserService.NOT_FOUND_ID_EXCEPTION, userId));
        }
        return true;
    }
}
