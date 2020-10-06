package ru.otus.spring.barsegyan.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.barsegyan.domain.Author;
import ru.otus.spring.barsegyan.domain.Book;
import ru.otus.spring.barsegyan.domain.Genre;
import ru.otus.spring.barsegyan.dto.CreateBookDto;
import ru.otus.spring.barsegyan.dto.UpdateBookDto;
import ru.otus.spring.barsegyan.service.BookService;

import java.util.stream.Collectors;

@ShellComponent
public class ApplicationCommands {
    private final BookService bookService;

    public ApplicationCommands(BookService bookService) {
        this.bookService = bookService;
    }

    @ShellMethod(value = "Get all books", key = "get-books")
    public String getBooks() {
        return bookService.getAllBooks()
                .stream()
                .map(this::formatBook)
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "Create book", key = "create-book")
    public void createBook(@ShellOption String title,
                           @ShellOption long genreId,
                           @ShellOption long authorId) {
        bookService.createBook(new CreateBookDto(title, authorId, genreId));
    }

    @ShellMethod(value = "Update book", key = "update-book")
    public void updateBook(@ShellOption long id,
                           @ShellOption String title,
                           @ShellOption long genreId,
                           @ShellOption long authorId) {
        bookService.updateBook(new UpdateBookDto(id, title, authorId, genreId));
    }

    @ShellMethod(value = "Delete book", key = "delete-book")
    public void deleteBookById(@ShellOption long id) {
        bookService.deleteBookById(id);
    }

    private String formatBook(Book book) {
        return String.format("Book \"%s\" (id: %s)", book.getTitle(), book.getId()) +
                "\n\t" + "Author: " + formatAuthor(book.getAuthor()) +
                "\n\t" + "Genre: " + formatGenre(book.getGenre());
    }

    private String formatGenre(Genre genre) {
        return String.format("%s (id: %s)", genre.getName(), genre.getId());
    }

    private String formatAuthor(Author author) {
        return String.format("%s (id: %s)", author.getName(), author.getId());
    }
}
