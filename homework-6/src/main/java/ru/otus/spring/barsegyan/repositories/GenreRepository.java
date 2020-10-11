package ru.otus.spring.barsegyan.repositories;

import ru.otus.spring.barsegyan.domain.Genre;

import java.util.List;

public interface GenreRepository {
    List<Genre> findAll();

    List<Genre> findAllByIds(List<Long> genreIds);

    Genre save(Genre genre);
}
