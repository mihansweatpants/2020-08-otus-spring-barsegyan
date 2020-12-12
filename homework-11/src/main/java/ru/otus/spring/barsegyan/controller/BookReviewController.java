package ru.otus.spring.barsegyan.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.barsegyan.domain.Book;
import ru.otus.spring.barsegyan.domain.BookReview;
import ru.otus.spring.barsegyan.dto.BookReviewDto;
import ru.otus.spring.barsegyan.dto.CreateBookReviewDto;
import ru.otus.spring.barsegyan.dto.mappers.BookReviewDtoMapper;
import ru.otus.spring.barsegyan.exception.NotFoundException;
import ru.otus.spring.barsegyan.repository.BookRepository;
import ru.otus.spring.barsegyan.repository.BookReviewRepository;

import java.time.LocalDateTime;

@RestController
public class BookReviewController {

    private final BookReviewRepository bookReviewRepository;
    private final BookRepository bookRepository;

    public BookReviewController(BookReviewRepository bookReviewRepository,
                                BookRepository bookRepository) {
        this.bookReviewRepository = bookReviewRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/api/books/{bookId}/reviews")
    public Flux<BookReviewDto> getAllByBookId(@PathVariable String bookId) {
        return bookReviewRepository
                .findAllByBookId(bookId)
                .map(BookReviewDtoMapper::toDto);
    }

    @PostMapping("/api/books/reviews")
    public Mono<BookReviewDto> createBookReview(@RequestBody CreateBookReviewDto createBookReviewDto) {
        return bookRepository
                .findById(createBookReviewDto.getBookId())
                .switchIfEmpty(Mono.error(new NotFoundException(String.format("Book with id=%s not found", createBookReviewDto.getBookId()))))
                .zipWhen(book -> bookReviewRepository.save(
                        new BookReview()
                                .setBook(book)
                                .setText(createBookReviewDto.getText())
                                .setCreatedAt(LocalDateTime.now())))
                .flatMap(tuple ->
                        updateRecentBookReviews(tuple.getT1())
                                .thenReturn(BookReviewDtoMapper.toDto(tuple.getT2())));
    }

    @DeleteMapping("/api/books/reviews/{reviewId}")
    public Mono<ResponseEntity<Void>> deleteBookReview(@PathVariable String reviewId) {
        return bookReviewRepository
                .findById(reviewId)
                .switchIfEmpty(Mono.error(new NotFoundException(String.format("Review with id=%s not found", reviewId))))
                .zipWhen(bookReview -> Mono.just(bookReview.getBook()))
                .flatMap(tuple ->
                        bookReviewRepository
                                .delete(tuple.getT1())
                                .then(updateRecentBookReviews(tuple.getT2())))
                .then(Mono.just(ResponseEntity.ok().build()));
    }

    private Mono<Book> updateRecentBookReviews(Book book) {
        return
                bookReviewRepository
                        .findAllByBookId(
                                book.getId(),
                                PageRequest.of(0, 10, Sort.by("createdAt").descending()))
                        .collectList()
                        .flatMap(bookReviews -> bookRepository.save(book.setRecentReviews(bookReviews)));
    }

}
