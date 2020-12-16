package ru.otus.spring.barsegyan.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.barsegyan.domain.Author;
import ru.otus.spring.barsegyan.domain.Book;
import ru.otus.spring.barsegyan.domain.Genre;
import ru.otus.spring.barsegyan.dto.CreateBookDto;
import ru.otus.spring.barsegyan.dto.UpdateBookDto;
import ru.otus.spring.barsegyan.exception.NotFoundException;
import ru.otus.spring.barsegyan.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookService(BookRepository bookRepository,
                       AuthorService authorService,
                       GenreService genreService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Transactional(readOnly = true)
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Book getBookById(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Book with id=%s not found", id)));
    }

    @Transactional
    public Book createBook(CreateBookDto createBookDto) {
        Book book = new Book();
        book.setTitle(createBookDto.getTitle());

        List<Genre> genres = genreService.getGenresByIds(createBookDto.getGenreIds());
        book.setGenres(genres);

        Author author = authorService.getAuthorById(createBookDto.getAuthorId());
        book.setAuthor(author);

        return bookRepository.save(book);
    }

    @Transactional
    public Book updateById(long bookId, UpdateBookDto updateBookDto) {
        Book bookToUpdate = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException(String.format("Book with id=%s not found", bookId)));

        Optional.ofNullable(updateBookDto.getTitle()).ifPresent(bookToUpdate::setTitle);
        Optional.ofNullable(updateBookDto.getAuthorId()).map(authorService::getAuthorById).ifPresent(bookToUpdate::setAuthor);
        Optional.ofNullable(updateBookDto.getGenreIds()).map(genreService::getGenresByIds).ifPresent(bookToUpdate::setGenres);

        return bookRepository.save(bookToUpdate);
    }

    @Transactional
    public void deleteBookById(long bookId) {
        bookRepository.deleteById(bookId);
    }
}
