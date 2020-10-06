package ru.otus.spring.barsegyan.dao;

import ru.otus.spring.barsegyan.domain.Author;

import java.util.List;

public interface AuthorDao {
    int count();

    List<Author> findAll();

    Author findById(long id);

    void create(Author author);
}
