package ru.otus.spring.barsegyan.repositories;

import ru.otus.spring.barsegyan.domain.BookReview;

import java.util.Optional;

public interface BookReviewRepository {
    Optional<BookReview> findById(long id);

    BookReview save(BookReview bookReview);

    void deleteById(long id);
}
