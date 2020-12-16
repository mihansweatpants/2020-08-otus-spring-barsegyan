package ru.otus.spring.barsegyan.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.barsegyan.domain.Book;
import ru.otus.spring.barsegyan.dto.BookDto;
import ru.otus.spring.barsegyan.dto.CreateBookDto;
import ru.otus.spring.barsegyan.dto.UpdateBookDto;
import ru.otus.spring.barsegyan.dto.mappers.BookDtoMapper;
import ru.otus.spring.barsegyan.dto.rest.ApiResponse;
import ru.otus.spring.barsegyan.dto.rest.Pagination;
import ru.otus.spring.barsegyan.service.BookService;

import java.util.stream.Collectors;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/api/books")
    public ApiResponse<Pagination<BookDto>> getAll(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int limit) {
        Page<Book> booksPage = bookService.getAllBooks(PageRequest.of(page, limit));

        return ApiResponse.ok(
                Pagination.of(
                        booksPage.stream().map(BookDtoMapper::toDto).collect(Collectors.toList()),
                        booksPage.getTotalPages(),
                        booksPage.getTotalElements()
                )
        );
    }

    @GetMapping("/api/books/{bookId}")
    public ApiResponse<BookDto> getById(@PathVariable long bookId) {
        return ApiResponse.ok(BookDtoMapper.toDto(bookService.getBookById(bookId)));
    }

    @PostMapping("/api/books")
    public ApiResponse<BookDto> create(@RequestBody CreateBookDto createBookDto) {
        Book createdBook = bookService.createBook(createBookDto);

        return ApiResponse.ok(BookDtoMapper.toDto(createdBook));
    }

    @PatchMapping("/api/books/{bookId}")
    public ApiResponse<BookDto> update(@PathVariable long bookId, @RequestBody UpdateBookDto updateBookDto) {
        Book updatedBook = bookService.updateById(bookId, updateBookDto);

        return ApiResponse.ok(BookDtoMapper.toDto(updatedBook));
    }

    @DeleteMapping("/api/books/{bookId}")
    public ApiResponse<Void> delete(@PathVariable long bookId) {
        bookService.deleteBookById(bookId);

        return ApiResponse.ok();
    }
}
