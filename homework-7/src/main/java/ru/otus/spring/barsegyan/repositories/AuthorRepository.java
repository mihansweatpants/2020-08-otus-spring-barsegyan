package ru.otus.spring.barsegyan.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.barsegyan.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends CrudRepository<Author, Long> {
    List<Author> findAll();

    Optional<Author> findById(long id);
}
