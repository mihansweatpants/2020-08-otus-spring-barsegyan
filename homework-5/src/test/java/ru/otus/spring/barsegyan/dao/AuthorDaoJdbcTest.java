package ru.otus.spring.barsegyan.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.barsegyan.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Import(AuthorDaoJdbc.class)
@DisplayName("Author dao jdbc should")
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @Test
    @DisplayName("return count of all authors")
    void shouldReturnCountOfAllAuthors() {
        int expectedCount = 2;
        int actualCount = authorDaoJdbc.count();

        assertThat(expectedCount).isEqualTo(actualCount);
    }

    @Test
    @DisplayName("find all authors")
    void shouldFindAllAuthors() {
        List<Author> expectedAuthorList = List.of(
                new Author(1, "Test author"),
                new Author(2, "Another author")
        );

        List<Author> actualAuthorList = authorDaoJdbc.findAll();

        assertThat(expectedAuthorList).isEqualTo(actualAuthorList);
    }

    @Test
    @DisplayName("find author by id")
    void shouldFindAuthorById() {
        Author expectedAuthor = new Author(1, "Test author");

        Author actualAuthor = authorDaoJdbc.findById(expectedAuthor.getId());

        assertThat(actualAuthor)
                .hasFieldOrPropertyWithValue("id", expectedAuthor.getId())
                .hasFieldOrPropertyWithValue("name", expectedAuthor.getName());
    }

    @Test
    void create() {
        Author newAuthor = new Author();
        newAuthor.setName("Tolstoy");

        authorDaoJdbc.create(newAuthor);

        assertThat(authorDaoJdbc.findAll())
                .anyMatch(author -> author.getName().equals(newAuthor.getName()));
    }
}