package ru.otus.spring.barsegyan.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.barsegyan.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(GenreRepositoryJpa.class)
@DisplayName("GenreRepositoryJpa should")
class GenreRepositoryJpaTest {

    private final static int EXPECTED_NUMBER_OF_GENRES = 2;
    private final static long FIRST_GENRE_ID = 1L;
    private final static long SECOND_GENRE_ID = 2L;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private GenreRepositoryJpa genreRepositoryJpa;

    @Test
    @DisplayName("find all genres")
    void findAll() {
        var genres = genreRepositoryJpa.findAll();

        assertThat(genres).isNotNull().hasSize(EXPECTED_NUMBER_OF_GENRES)
                .allMatch(genre -> !genre.getName().equals(""));
    }

    @Test
    @DisplayName("find all genres with given ids")
    void findAllByIds() {
        var genres = genreRepositoryJpa.findAllByIds(List.of(FIRST_GENRE_ID, SECOND_GENRE_ID));

        assertThat(genres).isNotNull().hasSize(2)
                .anyMatch(genre -> genre.getId() == FIRST_GENRE_ID)
                .anyMatch(genre -> genre.getId() == SECOND_GENRE_ID);
    }

    @Test
    @DisplayName("create new genre")
    void save() {
        var newGenre = new Genre();
        newGenre.setName("Детектив");

        genreRepositoryJpa.save(newGenre);
        assertThat(newGenre.getId()).isGreaterThan(0);

        var actualGenre = em.find(Genre.class, newGenre.getId());

        assertThat(actualGenre).isNotNull()
                .matches(genre -> genre.getName().equals(newGenre.getName()));
    }
}