package ru.otus.spring.barsegyan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.spring.barsegyan.domain.Author;

@RepositoryRestResource(path = "authors")
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
