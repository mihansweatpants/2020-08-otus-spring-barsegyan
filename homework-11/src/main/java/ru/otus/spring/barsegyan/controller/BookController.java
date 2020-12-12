package ru.otus.spring.barsegyan.controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.barsegyan.domain.Author;
import ru.otus.spring.barsegyan.domain.Book;
import ru.otus.spring.barsegyan.domain.Genre;
import ru.otus.spring.barsegyan.dto.BookDto;
import ru.otus.spring.barsegyan.dto.CreateBookDto;
import ru.otus.spring.barsegyan.dto.UpdateBookDto;
import ru.otus.spring.barsegyan.dto.mappers.BookDtoMapper;
import ru.otus.spring.barsegyan.exception.NotFoundException;
import ru.otus.spring.barsegyan.repository.AuthorRepository;
import ru.otus.spring.barsegyan.repository.BookRepository;
import ru.otus.spring.barsegyan.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    public BookController(BookRepository bookRepository,
                          GenreRepository genreRepository,
                          AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
    }

    @GetMapping("/api/books")
    public Flux<BookDto> getAll() {
        return bookRepository.findAll().map(BookDtoMapper::toDto);
    }

    @PostMapping("/api/books")
    public Mono<BookDto> create(@RequestBody CreateBookDto createBookDto) {
        Mono<List<Genre>> genres = genreRepository
                .findAllById(createBookDto.getGenreIds())
                .switchIfEmpty(Mono.error(new NotFoundException("Genres with given ids not found")))
                .collectList();

        Mono<Author> author = authorRepository
                .findById(createBookDto.getAuthorId())
                .switchIfEmpty(Mono.error(new NotFoundException(String.format("Author with id=%s not found", createBookDto.getAuthorId()))));

        return Mono.zip(genres, author)
                .flatMap(tuple -> bookRepository
                        .save(new Book()
                                .setTitle(createBookDto.getTitle())
                                .setGenres(tuple.getT1())
                                .setAuthor(tuple.getT2())))
                .map(BookDtoMapper::toDto);
    }

    @PatchMapping("/api/books/{bookId}")
    public Mono<BookDto> updateBook(@PathVariable String bookId, @RequestBody UpdateBookDto updateBookDto) {
        Mono<Optional<Author>> author = Mono.justOrEmpty(updateBookDto.getAuthorId())
                .flatMap(authorId ->
                        authorRepository.findById(authorId)
                                .switchIfEmpty(Mono.error(new NotFoundException(String.format("Author with id=%s not found", authorId)))))
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty());

        Mono<Optional<List<Genre>>> genres = Mono.justOrEmpty(updateBookDto.getGenreIds())
                .flatMap(genreIds ->
                        genreRepository.findAllById(genreIds)
                                .switchIfEmpty(Mono.error(new NotFoundException("Genres with given ids not found")))
                                .collectList())
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty());

        return Mono.zip(bookRepository
                        .findById(bookId)
                        .switchIfEmpty(Mono.error(new NotFoundException(String.format("Book with id=%s not found", bookId)))),
                genres,
                author)
                .flatMap(tuple -> {
                    Book bookToUpdate = tuple.getT1();

                    tuple.getT2().ifPresent(bookToUpdate::setGenres);
                    tuple.getT3().ifPresent(bookToUpdate::setAuthor);
                    Optional.ofNullable(updateBookDto.getTitle()).ifPresent(bookToUpdate::setTitle);

                    return bookRepository.save(bookToUpdate);
                })
                .map(BookDtoMapper::toDto);
    }
}
