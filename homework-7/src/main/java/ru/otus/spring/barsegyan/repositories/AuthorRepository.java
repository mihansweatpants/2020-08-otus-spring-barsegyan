package ru.otus.spring.barsegyan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.barsegyan.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {}
