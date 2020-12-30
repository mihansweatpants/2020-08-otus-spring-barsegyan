package ru.otus.spring.barsegyan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.spring.barsegyan.domain.BookReview;

@RepositoryRestResource(path = "reviews")
public interface BookReviewRepository extends JpaRepository<BookReview, Long> {
}
