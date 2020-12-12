package ru.otus.spring.barsegyan.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.spring.barsegyan.domain.BookReview;

public interface BookReviewRepository extends ReactiveMongoRepository<BookReview, String> {
    Flux<BookReview> findAllByBookId(String bookId);
    Flux<BookReview> findAllByBookId(String bookId, Pageable pageable);
}
