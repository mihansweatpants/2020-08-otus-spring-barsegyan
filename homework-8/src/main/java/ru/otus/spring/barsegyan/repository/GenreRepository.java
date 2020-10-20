package ru.otus.spring.barsegyan.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.barsegyan.domain.Genre;

import java.util.List;

public interface GenreRepository extends MongoRepository<Genre, String> {
    List<Genre> findAllById(Iterable<String> ids);
}
