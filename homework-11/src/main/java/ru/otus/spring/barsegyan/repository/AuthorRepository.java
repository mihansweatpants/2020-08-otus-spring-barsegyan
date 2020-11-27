package ru.otus.spring.barsegyan.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring.barsegyan.domain.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
}
