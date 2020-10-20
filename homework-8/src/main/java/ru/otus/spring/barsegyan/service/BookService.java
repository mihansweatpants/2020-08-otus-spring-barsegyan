package ru.otus.spring.barsegyan.service;

import org.springframework.stereotype.Service;
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
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository,
                       GenreRepository genreRepository,
                       AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
    }

    public List<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDto getBookById(String id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Book with id=%s not found", id)));

        return BookDtoMapper.toDto(book);
    }

    public BookDto createBook(CreateBookDto createBookDto) {
        Book book = new Book();
        book.setTitle(createBookDto.getTitle());

        List<Genre> genres = genreRepository.findAllById(createBookDto.getGenreIds());
        book.setGenres(genres);

        Author author = authorRepository.findById(createBookDto.getAuthorId())
                .orElseThrow(() -> new NotFoundException(String.format("Author with id=%s not found", createBookDto.getAuthorId())));
        book.setAuthor(author);

        return BookDtoMapper.toDto(bookRepository.save(book));
    }

    public BookDto updateBook(UpdateBookDto updateBookDto) {
        Book bookToUpdate = bookRepository.findById(updateBookDto.getBookId())
                .orElseThrow(() -> new NotFoundException(String.format("Book with id=%s not found", updateBookDto.getBookId())));

        bookToUpdate.setTitle(updateBookDto.getTitle());

        Author author = authorRepository.findById(updateBookDto.getAuthorId())
                .orElseThrow(() -> new NotFoundException(String.format("Author with id=%s not found", updateBookDto.getAuthorId())));
        bookToUpdate.setAuthor(author);

        List<Genre> genres = genreRepository.findAllById(updateBookDto.getGenreIds());
        bookToUpdate.setGenres(genres);

        return BookDtoMapper.toDto(bookRepository.save(bookToUpdate));
    }

    public void deleteBookById(String bookId) {
        bookRepository.deleteById(bookId);
    }
}
