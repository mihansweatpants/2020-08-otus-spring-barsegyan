package ru.otus.spring.barsegyan.repositories;

import ru.otus.spring.barsegyan.domain.BookReview;

import java.util.List;
import java.util.Optional;

public interface BookReviewRepository {
    List<BookReview> findAllByBookId(long bookId);

    Optional<BookReview> findById(long id);

    BookReview save(BookReview bookReview);

    void deleteById(long id);
}
