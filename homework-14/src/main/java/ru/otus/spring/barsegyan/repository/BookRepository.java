package ru.otus.spring.barsegyan.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.barsegyan.domain.Book;

public interface BookRepository extends MongoRepository<Book, String> {
}
