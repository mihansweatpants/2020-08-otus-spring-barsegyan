package ru.otus.spring.barsegyan.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.barsegyan.domain.Author;
import ru.otus.spring.barsegyan.domain.Book;
import ru.otus.spring.barsegyan.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BookRepository should")
@DataJpaTest
class BookRepositoryTest {

    private final static int EXPECTED_NUMBER_OF_BOOKS = 2;
    private final static long FIRST_BOOK_ID = 1L;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("find all books with all associated data")
    void findAll() {
        var books = bookRepository.findAll();

        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(book -> !book.getTitle().equals(""))
                .allMatch(book -> book.getAuthor() != null)
                .allMatch(book -> book.getGenres() != null && book.getGenres().size() > 0);
    }

    @Test
    @DisplayName("find book by id")
    void findById() {
        var optionalExpectedBook = bookRepository.findById(FIRST_BOOK_ID);
        var actualBook = em.find(Book.class, FIRST_BOOK_ID);

        assertThat(optionalExpectedBook).isPresent().get()
                .isEqualToComparingFieldByField(actualBook);
    }

    @Test
    @DisplayName("save book with all associated data")
    void save() {
        var genre = new Genre();
        genre.setName("Поэма");

        var author = new Author();
        author.setName("Лермонтов М.Ю.");

        var newBook = new Book();
        newBook.setTitle("Демон");
        newBook.setGenres(List.of(genre));
        newBook.setAuthor(author);

        bookRepository.save(newBook);
        assertThat(newBook.getId()).isGreaterThan(0);

        var actualBook = em.find(Book.class, newBook.getId());
        assertThat(actualBook).isNotNull()
                .matches(book -> !book.getTitle().equals(""))
                .matches(book -> book.getGenres() != null && book.getGenres().size() > 0 && book.getGenres().get(0).getId() > 0)
                .matches(book -> book.getAuthor() != null && book.getAuthor().getId() > 0);

        assertThat(bookRepository.findAll()).size().isGreaterThan(EXPECTED_NUMBER_OF_BOOKS);
    }

    @Test
    @DisplayName("update book")
    void update() {
        var bookToUpdate = em.find(Book.class, FIRST_BOOK_ID);
        bookToUpdate.setTitle("Another Title");

        assertThat(bookRepository.save(bookToUpdate))
                .matches(updatedBook -> updatedBook.getTitle().equals(bookToUpdate.getTitle()));
    }

    @Test
    @DisplayName("delete book by id")
    void deleteById() {
        var book = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(book).isNotNull();
        em.detach(book);

        bookRepository.deleteById(FIRST_BOOK_ID);
        var deletedBook = em.find(Book.class, FIRST_BOOK_ID);

        assertThat(deletedBook).isNull();
    }
}