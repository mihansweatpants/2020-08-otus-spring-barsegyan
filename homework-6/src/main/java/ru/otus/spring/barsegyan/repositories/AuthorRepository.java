package ru.otus.spring.barsegyan.repositories;

import ru.otus.spring.barsegyan.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    List<Author> findAll();

    Optional<Author> findById(long id);

    Author save(Author author);
}
