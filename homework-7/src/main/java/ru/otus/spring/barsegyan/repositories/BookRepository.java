package ru.otus.spring.barsegyan.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.barsegyan.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findAll();

    Optional<Book> findById(long id);

    void deleteById(long id);
}
