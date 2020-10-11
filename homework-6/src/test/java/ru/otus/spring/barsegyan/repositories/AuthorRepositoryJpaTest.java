package ru.otus.spring.barsegyan.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.barsegyan.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("AuthorRepositoryJpa should")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {

    private final static int EXPECTED_NUMBER_OF_AUTHORS = 2;
    private final static long FIRST_AUTHOR_ID = 1L;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private AuthorRepositoryJpa authorRepositoryJpa;

    @Test
    @DisplayName("find all authors")
    void findAll() {
        var authors = authorRepositoryJpa.findAll();

        assertThat(authors).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHORS)
                .allMatch(author -> !author.getName().equals(""));
    }

    @Test
    @DisplayName("find author by id")
    void findById() {
        var optionalExpectedAuthor = authorRepositoryJpa.findById(FIRST_AUTHOR_ID);
        var actualAuthor = em.find(Author.class, FIRST_AUTHOR_ID);

        assertThat(optionalExpectedAuthor).isPresent().get()
                .isEqualToComparingFieldByField(actualAuthor);
    }

    @Test
    @DisplayName("create new author")
    void save() {
        var newAuthor = new Author();
        newAuthor.setName("Солженицын А.И.");

        authorRepositoryJpa.save(newAuthor);
        assertThat(newAuthor.getId()).isGreaterThan(0);

        var actualAuthor = em.find(Author.class, newAuthor.getId());

        assertThat(actualAuthor).isNotNull()
                .matches(author -> author.getName().equals(newAuthor.getName()));
    }
}