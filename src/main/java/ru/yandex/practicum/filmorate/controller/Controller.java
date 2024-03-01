package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.service.Service;

import java.util.List;

@RequiredArgsConstructor
public abstract class Controller<T extends Model> {

    protected final Service<T> service;


    public T add(T model) throws ValidateException {

        return service.addModel(model);
    }


    public T update(T model) throws NotFoundException {
        service.updateModel(model);
        return model;
    }

    @GetMapping
    public List<T> getModelList() {
        return service.getModelList();
    }

    public T get(int id) throws NotFoundException {
        T model = service.getModelById(id);

        return model;
    }
}
