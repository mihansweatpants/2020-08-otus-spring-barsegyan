package ru.otus.spring.barsegyan.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.barsegyan.domain.BookReview;

import java.util.List;

public interface BookReviewRepository extends MongoRepository<BookReview, String> {
    List<BookReview> findAllByBookId(String bookId);
    List<BookReview> findAllByBookId(String bookId, Pageable pageable);
}
