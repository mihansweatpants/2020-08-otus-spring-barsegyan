package ru.otus.spring.barsegyan.repositories;

import org.springframework.stereotype.Repository;
import ru.otus.spring.barsegyan.domain.BookReview;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class BookReviewRepositoryJpa implements BookReviewRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<BookReview> findById(long id) {
        return Optional.ofNullable(em.find(BookReview.class, id));
    }

    @Override
    public BookReview save(BookReview bookReview) {
        if (bookReview.getId() == 0) {
            em.persist(bookReview);
            return bookReview;
        } else {
            return em.merge(bookReview);
        }
    }

    @Override
    public void deleteById(long id) {
        em.createQuery("delete from BookReview br where br.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
