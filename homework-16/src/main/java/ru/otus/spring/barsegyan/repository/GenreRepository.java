package ru.otus.spring.barsegyan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.spring.barsegyan.domain.Genre;

@RepositoryRestResource(path = "genres")
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
