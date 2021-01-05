package ru.otus.spring.barsegyan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.barsegyan.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {}
