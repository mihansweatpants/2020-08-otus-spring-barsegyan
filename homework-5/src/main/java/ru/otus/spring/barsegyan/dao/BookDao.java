package ru.otus.spring.barsegyan.dao;

import ru.otus.spring.barsegyan.domain.Book;

import java.util.List;

public interface BookDao {
    int count();

    List<Book> findAll();

    Book findById(long id);

    void create(Book book);

    void update(Book book);

    void deleteById(long id);
}
