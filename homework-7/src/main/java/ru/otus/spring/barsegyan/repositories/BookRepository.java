package ru.otus.spring.barsegyan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.barsegyan.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {}
