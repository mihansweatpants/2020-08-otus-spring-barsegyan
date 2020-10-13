package ru.otus.spring.barsegyan.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.barsegyan.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Import(GenreDaoJdbc.class)
@DisplayName("Genre dao jdbc should")
class GenreDaoJdbcTest {

    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    @Test
    @DisplayName("return count of all genres")
    void shouldReturnCountOfAllGenres() {
        int expectedCount = 2;
        int actualCount = genreDaoJdbc.count();

        assertThat(expectedCount).isEqualTo(actualCount);
    }

    @Test
    @DisplayName("find all genres")
    void shouldFindAllGenres() {
        List<Genre> expectedGenreList = List.of(
                new Genre(1, "Test genre"),
                new Genre(2, "Another genre")
        );

        List<Genre> actualGenreList = genreDaoJdbc.findAll();

        assertThat(expectedGenreList).isEqualTo(actualGenreList);
    }

    @Test
    @DisplayName("find genre by id")
    void shouldFindAuthorById() {
        Genre expectedGenre = new Genre(1, "Test genre");

        Genre actualGenre = genreDaoJdbc.findById(expectedGenre.getId());

        assertThat(actualGenre)
                .hasFieldOrPropertyWithValue("id", expectedGenre.getId())
                .hasFieldOrPropertyWithValue("name", expectedGenre.getName());
    }

    @Test
    void create() {
        Genre newGenre = new Genre();
        newGenre.setName("Novel");

        genreDaoJdbc.create(newGenre);

        assertThat(genreDaoJdbc.findAll())
                .anyMatch(genre -> genre.getName().equals(newGenre.getName()));
    }
}