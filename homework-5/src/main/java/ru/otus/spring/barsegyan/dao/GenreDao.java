package ru.otus.spring.barsegyan.dao;

import ru.otus.spring.barsegyan.domain.Genre;

import java.util.List;

public interface GenreDao {
    int count();

    List<Genre> findAll();

    Genre findById(long id);

    void create(Genre genre);
}
