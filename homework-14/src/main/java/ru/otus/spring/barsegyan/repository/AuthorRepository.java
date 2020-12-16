package ru.otus.spring.barsegyan.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.barsegyan.domain.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {
}
