package ru.otus.spring.barsegyan.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.barsegyan.dto.BookReviewDto;
import ru.otus.spring.barsegyan.dto.CreateBookReviewDto;
import ru.otus.spring.barsegyan.service.BookReviewService;

import java.util.stream.Collectors;

@ShellComponent
public class BookReviewCommands {

    private final BookReviewService bookReviewService;

    public BookReviewCommands(BookReviewService bookReviewService) {
        this.bookReviewService = bookReviewService;
    }

    @ShellMethod(value = "Get all book reviews", key = "get-reviews")
    public String getReviewsByBookId(@ShellOption String bookId) {
        return bookReviewService.getAllReviewsByBookId(bookId)
                .stream()
                .map(this::formatReview)
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "Add book review", key = "add-review")
    public String addReview(@ShellOption String bookId,
                            @ShellOption String text) {
        return formatReview(bookReviewService.createBookReview(new CreateBookReviewDto(text, bookId)));
    }

    @ShellMethod(value = "Delete book review", key = "delete-review")
    public void deleteReview(@ShellOption String reviewId) {
        bookReviewService.deleteBookReview(reviewId);
    }

    private String formatReview(BookReviewDto bookReviewDto) {
        return String.format("Review for book \"%s\": %s (id: %s)",
                bookReviewDto.getBookTitle(),
                bookReviewDto.getText(),
                bookReviewDto.getId());
    }
}
