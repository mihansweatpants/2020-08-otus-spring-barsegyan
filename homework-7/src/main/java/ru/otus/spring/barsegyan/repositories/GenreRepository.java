package ru.otus.spring.barsegyan.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.barsegyan.domain.Genre;

import java.util.List;

public interface GenreRepository extends CrudRepository<Genre, Long> {
    List<Genre> findAll();

    List<Genre> findByIdIn(List<Long> genreIds);
}
