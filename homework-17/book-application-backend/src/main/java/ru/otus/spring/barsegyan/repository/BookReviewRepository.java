package ru.otus.spring.barsegyan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.barsegyan.domain.BookReview;

public interface BookReviewRepository extends JpaRepository<BookReview, Long> {
    Page<BookReview> findAllByBookId(long bookId, Pageable pageable);
}
