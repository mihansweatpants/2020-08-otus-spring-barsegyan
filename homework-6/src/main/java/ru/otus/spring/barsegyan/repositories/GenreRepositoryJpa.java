package ru.otus.spring.barsegyan.repositories;

import org.springframework.stereotype.Repository;
import ru.otus.spring.barsegyan.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class GenreRepositoryJpa implements GenreRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Genre> findAll() {
        return em.createQuery("select g from Genre g", Genre.class)
                .getResultList();
    }

    @Override
    public List<Genre> findAllByIds(List<Long> genreIds) {
        return em.createQuery("select g from Genre g where g.id in :ids", Genre.class)
                .setParameter("ids", genreIds)
                .getResultList();
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == 0) {
            em.persist(genre);
            return genre;
        } else {
            return em.merge(genre);
        }
    }
}
