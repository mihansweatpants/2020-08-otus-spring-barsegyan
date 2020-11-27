package ru.otus.spring.barsegyan.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring.barsegyan.domain.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
}
