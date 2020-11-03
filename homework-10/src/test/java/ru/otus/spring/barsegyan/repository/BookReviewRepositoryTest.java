package ru.otus.spring.barsegyan.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.barsegyan.domain.Book;
import ru.otus.spring.barsegyan.domain.BookReview;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("BookReviewRepository should")
class BookReviewRepositoryTest {

    private final static int EXPECTED_NUMBER_OF_FIRST_BOOK_REVIEWS = 2;
    private final static long FIRST_BOOK_REVIEW_ID = 1L;
    private final static long FIRST_BOOK_ID = 1L;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookReviewRepository bookReviewRepository;

    @Test
    @DisplayName("find all book reviews by book id")
    void findAll() {
        var bookReviews = bookReviewRepository.findAllByBookId(FIRST_BOOK_ID, null);

        assertThat(bookReviews).isNotNull().hasSize(EXPECTED_NUMBER_OF_FIRST_BOOK_REVIEWS)
                .allMatch(bookReview -> !bookReview.getText().equals(""));
    }

    @Test
    @DisplayName("find book review by id")
    void findById() {
        var optionalExpectedBookReview = bookReviewRepository.findById(FIRST_BOOK_REVIEW_ID);
        var actualBookReview = em.find(BookReview.class, FIRST_BOOK_REVIEW_ID);

        assertThat(optionalExpectedBookReview).isPresent().get()
                .isEqualToComparingFieldByField(actualBookReview);
    }

    @Test
    @DisplayName("create book review")
    void save() {
        var newBookReview = new BookReview();
        newBookReview.setText("Text of review");
        newBookReview.setBook(em.find(Book.class, FIRST_BOOK_ID));

        bookReviewRepository.save(newBookReview);
        assertThat(newBookReview.getId()).isGreaterThan(0);

        var actualBookReview = em.find(BookReview.class, newBookReview.getId());
        assertThat(actualBookReview).isNotNull()
                .matches(bookReview -> bookReview.getText().equals(newBookReview.getText()))
                .matches(bookReview -> bookReview.getBook().equals(newBookReview.getBook()));
    }

    @Test
    @DisplayName("delete book review by id")
    void deleteById() {
        var bookReview = em.find(Book.class, FIRST_BOOK_REVIEW_ID);
        assertThat(bookReview).isNotNull();
        em.detach(bookReview);

        bookReviewRepository.deleteById(FIRST_BOOK_REVIEW_ID);
        var deletedBookReview = em.find(BookReview.class, FIRST_BOOK_REVIEW_ID);

        assertThat(deletedBookReview).isNull();
    }
}