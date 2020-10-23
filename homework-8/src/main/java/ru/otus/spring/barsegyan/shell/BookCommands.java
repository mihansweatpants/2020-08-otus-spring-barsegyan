package ru.otus.spring.barsegyan.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.barsegyan.dto.*;
import ru.otus.spring.barsegyan.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class BookCommands {

    private final BookService bookService;

    public BookCommands(BookService bookService) {
        this.bookService = bookService;
    }

    @ShellMethod(value = "Get all books", key = "get-books")
    public String getBooks() {
        return bookService.getAllBooks()
                .stream()
                .map(this::formatBook)
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "Get book by id", key = "get-book")
    public String getBookById(@ShellOption String id) {
        return formatBook(bookService.getBookById(id));
    }

    @ShellMethod(value = "Create book", key = "create-book")
    public String createBook(@ShellOption String title,
                             @ShellOption List<String> genreIds,
                             @ShellOption String authorId) {
        return formatBook(bookService.createBook(new CreateBookDto(title, authorId, genreIds)));
    }

    @ShellMethod(value = "Update book", key = "update-book")
    public String updateBook(@ShellOption String id,
                             @ShellOption String title,
                             @ShellOption List<String> genreIds,
                             @ShellOption String authorId) {
        return formatBook(bookService.updateBook(new UpdateBookDto(id, title, authorId, genreIds)));
    }

    @ShellMethod(value = "Delete book", key = "delete-book")
    public void deleteBookById(@ShellOption String id) {
        bookService.deleteBookById(id);
    }

    private String formatBook(BookDto bookDto) {
        return String.format("Book \"%s\" (id: %s)", bookDto.getTitle(), bookDto.getId()) +
                "\n\t" + "Author: " + formatAuthor(bookDto.getAuthor()) +
                "\n\t" + "Genres: " + bookDto.getGenres()
                .stream()
                .map(this::formatGenre)
                .collect(Collectors.joining(", ")) +
                "\n\t" + "Recent reviews:\n\t\t" + bookDto.getRecentReviews()
                .stream()
                .map(this::formatReview)
                .collect(Collectors.joining(",\n\t\t"));
    }

    private String formatGenre(GenreDto genreDto) {
        return String.format("%s (id: %s)", genreDto.getName(), genreDto.getId());
    }

    private String formatAuthor(AuthorDto authorDto) {
        return String.format("%s (id: %s)", authorDto.getName(), authorDto.getId());
    }

    private String formatReview(BookReviewDto bookReviewDto) {
        return String.format("%s (id: %s)", bookReviewDto.getText(), bookReviewDto.getId());
    }
}