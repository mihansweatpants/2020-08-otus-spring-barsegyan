package ru.otus.spring.barsegyan.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring.barsegyan.domain.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {
}
