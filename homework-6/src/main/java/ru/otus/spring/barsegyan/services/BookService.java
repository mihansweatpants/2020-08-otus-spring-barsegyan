package ru.otus.spring.barsegyan.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.barsegyan.domain.Author;
import ru.otus.spring.barsegyan.domain.Book;
import ru.otus.spring.barsegyan.domain.Genre;
import ru.otus.spring.barsegyan.dto.BookDto;
import ru.otus.spring.barsegyan.dto.BookDtoShort;
import ru.otus.spring.barsegyan.dto.CreateBookDto;
import ru.otus.spring.barsegyan.dto.UpdateBookDto;
import ru.otus.spring.barsegyan.dto.mappers.BookDtoMapper;
import ru.otus.spring.barsegyan.exception.NotFoundException;
import ru.otus.spring.barsegyan.repositories.AuthorRepository;
import ru.otus.spring.barsegyan.repositories.BookRepository;
import ru.otus.spring.barsegyan.repositories.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookService(BookRepository bookRepository,
                           AuthorRepository authorRepository,
                           GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Transactional(readOnly = true)
    public List<BookDtoShort> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookDtoMapper::toShortDto)
                .collect(Collectors.toList());
    }

    public BookDto getBookById(long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Book with id=%s not found", id)));

        return BookDtoMapper.toDto(book);
    }

    @Transactional
    public BookDto createBook(CreateBookDto createBookDto) {
        Book book = new Book();
        book.setTitle(createBookDto.getTitle());

        List<Genre> genres = genreRepository.findAllByIds(createBookDto.getGenreIds());
        book.setGenres(genres);

        Author author = authorRepository.findById(createBookDto.getAuthorId())
                .orElseThrow(() -> new NotFoundException(String.format("Author with id=%s not found", createBookDto.getAuthorId())));
        book.setAuthor(author);

        return BookDtoMapper.toDto(bookRepository.save(book));
    }

    @Transactional
    public BookDto updateBook(UpdateBookDto updateBookDto) {
        Book bookToUpdate = bookRepository.findById(updateBookDto.getBookId())
                .orElseThrow(() -> new NotFoundException(String.format("Book with id=%s not found", updateBookDto.getBookId())));

        bookToUpdate.setTitle(updateBookDto.getTitle());

        Author author = authorRepository.findById(updateBookDto.getAuthorId())
                .orElseThrow(() -> new NotFoundException(String.format("Author with id=%s not found", updateBookDto.getAuthorId())));
        bookToUpdate.setAuthor(author);

        List<Genre> genres = genreRepository.findAllByIds(updateBookDto.getGenreIds());
        bookToUpdate.setGenres(genres);

        return BookDtoMapper.toDto(bookRepository.save(bookToUpdate));
    }

    @Transactional
    public void deleteBookById(long bookId) {
        bookRepository.deleteById(bookId);
    }
}
