package ru.otus.spring.barsegyan.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.barsegyan.domain.BookReview;
import ru.otus.spring.barsegyan.dto.BookReviewDto;
import ru.otus.spring.barsegyan.dto.CreateBookReviewDto;
import ru.otus.spring.barsegyan.dto.mappers.BookReviewDtoMapper;
import ru.otus.spring.barsegyan.dto.rest.ApiResponse;
import ru.otus.spring.barsegyan.dto.rest.Pagination;
import ru.otus.spring.barsegyan.service.BookReviewService;

import java.util.stream.Collectors;

@RestController
public class BookReviewController {

    private final BookReviewService bookReviewService;

    public BookReviewController(BookReviewService bookReviewService) {
        this.bookReviewService = bookReviewService;
    }

    @GetMapping("/api/books/{bookId}/reviews")
    public ApiResponse<Pagination<BookReviewDto>> getAll(@PathVariable long bookId,
                                                         @RequestParam(defaultValue = "createdAt") String sortBy,
                                                         @RequestParam(defaultValue = "desc") String direction,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int limit) {
        Page<BookReview> reviewsPage = bookReviewService.getAllReviewsByBookId(bookId,
                PageRequest.of(page, limit, Sort.by(Sort.Direction.fromString(direction), sortBy)));

        return ApiResponse.ok(
                Pagination.of(
                        reviewsPage.stream().map(BookReviewDtoMapper::toDto).collect(Collectors.toList()),
                        reviewsPage.getTotalPages(),
                        reviewsPage.getTotalElements()
                )
        );
    }

    @PostMapping("/api/books/reviews")
    public ApiResponse<BookReviewDto> addReview(@RequestBody CreateBookReviewDto createBookReviewDto) {
        return ApiResponse.ok(
                BookReviewDtoMapper.toDto(bookReviewService.createBookReview(createBookReviewDto)));
    }

    @DeleteMapping("/api/books/reviews/{reviewId}")
    public ApiResponse<Void> delete(@PathVariable long reviewId) {
        bookReviewService.deleteBookReview(reviewId);

        return ApiResponse.ok();
    }
}
