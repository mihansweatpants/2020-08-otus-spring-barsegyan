package ru.otus.spring.barsegyan.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.barsegyan.domain.BookReview;

import java.util.List;
import java.util.Optional;

public interface BookReviewRepository extends CrudRepository<BookReview, Long> {
    List<BookReview> findAllByBookId(long bookId);

    Optional<BookReview> findById(long id);

    void deleteById(long id);
}
