package ru.otus.spring.barsegyan.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.spring.barsegyan.dto.CreateBookReviewDto;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        properties = {
                InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
                ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
        }
)
@DisplayName("BookReviewService should")
class BookReviewServiceTest {

    @Autowired
    BookService bookService;

    @Autowired
    BookReviewService bookReviewService;

    @Test
    @DisplayName("create book review and update recent book reviews")
    void createBookReview() {
        var firstBook = bookService.getAllBooks().get(0);
        assertThat(firstBook).isNotNull();

        var createBookReviewDto = new CreateBookReviewDto("Test review", firstBook.getId());
        var createdReview = bookReviewService.createBookReview(createBookReviewDto);

        assertThat(createdReview).isNotNull()
                .matches(review -> !review.getId().equals(""))
                .matches(review -> review.getBookTitle().equals(firstBook.getTitle()))
                .matches(review -> review.getText().equals(createBookReviewDto.getText()));

        var resentReviews = bookService.getBookById(firstBook.getId()).getRecentReviews();
        assertThat(resentReviews).contains(createdReview);
    }

    @Test
    @DisplayName("delete book review and update recent book reviews")
    void deleteBookReview() {
        var firstBook = bookService.getAllBooks().get(0);
        assertThat(firstBook).isNotNull();

        var review = firstBook.getRecentReviews().get(0);
        assertThat(review).isNotNull();

        bookReviewService.deleteBookReview(review.getId());

        assertThat(bookService.getBookById(firstBook.getId()).getRecentReviews()).doesNotContain(review);
    }
}