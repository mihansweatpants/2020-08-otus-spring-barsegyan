package ru.otus.spring.barsegyan.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.barsegyan.domain.Author;
import ru.otus.spring.barsegyan.domain.Book;
import ru.otus.spring.barsegyan.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Import(BookDaoJdbc.class)
@DisplayName("Book dao jdbc should")
class BookDaoJdbcTest {

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @Test
    @DisplayName("return count of all books")
    void shouldReturnCountOfAllBooks() {
        int expectedCount = 2;
        int actualCount = bookDaoJdbc.count();

        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @Test
    @DisplayName("find all books")
    void shouldFindAllBooks() {
        List<Book> expectedBookList = List.of(
                new Book(1, "Test book", new Genre(1, "Test genre"), new Author(1, "Test author")),
                new Book(2, "Delete me", new Genre(2, "Another genre"), new Author(1, "Test author"))
        );

        List<Book> actualBookList = bookDaoJdbc.findAll();

        assertThat(actualBookList).isEqualTo(expectedBookList);
    }

    @Test
    @DisplayName("find book by id")
    void shouldFindBookById() {
        Genre sampleGenre = new Genre(1, "Test genre");
        Author sampleAuthor = new Author(1, "Test author");
        Book sampleBook = new Book(1, "Test book", sampleGenre, sampleAuthor);

        Book actualBook = bookDaoJdbc.findById(sampleBook.getId());

        assertThat(actualBook)
                .hasFieldOrPropertyWithValue("id", sampleBook.getId())
                .hasFieldOrPropertyWithValue("title", sampleBook.getTitle())
                .hasFieldOrPropertyWithValue("genre", sampleBook.getGenre())
                .hasFieldOrPropertyWithValue("author", sampleBook.getAuthor());
    }

    @Test
    @DisplayName("create book")
    void shouldCreateBook() {
        Genre sampleGenre = new Genre(1, "Test genre");
        Author sampleAuthor = new Author(1, "Test author");
        Book newBook = new Book();
        newBook.setTitle("Some book");
        newBook.setGenre(sampleGenre);
        newBook.setAuthor(sampleAuthor);

        bookDaoJdbc.create(newBook);

        assertThat(bookDaoJdbc.findAll())
                .anyMatch(book -> book.getTitle().equals(newBook.getTitle())
                        && book.getAuthor().equals(newBook.getAuthor())
                        && book.getGenre().equals(newBook.getGenre()));
    }

    @Test
    @DisplayName("update book")
    void shouldUpdateBook() {
        Genre sampleGenre = new Genre(1, "Test genre");
        Author sampleAuthor = new Author(1, "Test author");
        Book sampleBook = new Book(1, "Test book", sampleGenre, sampleAuthor);

        String updatedTitle = "Another title";
        Genre updatedGenre = new Genre(2, "Another genre");
        Author updatedAuthor = new Author(2, "Another author");
        sampleBook.setTitle(updatedTitle);
        sampleBook.setGenre(updatedGenre);
        sampleBook.setAuthor(updatedAuthor);

        bookDaoJdbc.update(sampleBook);

        assertThat(bookDaoJdbc.findById(sampleBook.getId()))
                .hasFieldOrPropertyWithValue("title", updatedTitle)
                .hasFieldOrPropertyWithValue("genre", updatedGenre)
                .hasFieldOrPropertyWithValue("author", updatedAuthor);
    }

    @Test
    @DisplayName("delete book by id")
    void shouldDeleteBookById() {
        Genre sampleGenre = new Genre(1, "Test genre");
        Author sampleAuthor = new Author(1, "Test author");
        Book bookToDelete = new Book(3, "Delete me", sampleGenre, sampleAuthor);

        bookDaoJdbc.deleteById(bookToDelete.getId());

        assertThat(bookDaoJdbc.findAll()).doesNotContain(bookToDelete);
    }
}