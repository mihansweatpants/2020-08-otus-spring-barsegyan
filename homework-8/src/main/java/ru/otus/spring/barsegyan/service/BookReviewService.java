package ru.otus.spring.barsegyan.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.otus.spring.barsegyan.domain.Book;
import ru.otus.spring.barsegyan.domain.BookReview;
import ru.otus.spring.barsegyan.dto.BookReviewDto;
import ru.otus.spring.barsegyan.dto.CreateBookReviewDto;
import ru.otus.spring.barsegyan.dto.mappers.BookReviewDtoMapper;
import ru.otus.spring.barsegyan.exception.NotFoundException;
import ru.otus.spring.barsegyan.repository.BookRepository;
import ru.otus.spring.barsegyan.repository.BookReviewRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookReviewService {

    private final BookReviewRepository bookReviewRepository;
    private final BookRepository bookRepository;

    public BookReviewService(BookReviewRepository bookReviewRepository,
                             BookRepository bookRepository) {
        this.bookReviewRepository = bookReviewRepository;
        this.bookRepository = bookRepository;
    }

    public List<BookReviewDto> getAllReviewsByBookId(String bookId) {
        return bookReviewRepository.findAllByBookId(bookId)
                .stream()
                .map(BookReviewDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookReviewDto createBookReview(CreateBookReviewDto createBookReviewDto) {
        Book book = bookRepository.findById(createBookReviewDto.getBookId())
                .orElseThrow(() -> new NotFoundException(String.format("Book with id=%s not found", createBookReviewDto.getBookId())));

        BookReview bookReview = new BookReview();
        bookReview.setBook(book);
        bookReview.setText(createBookReviewDto.getText());
        bookReview.setCreatedAt(LocalDateTime.now());
        bookReviewRepository.save(bookReview);

        updateRecentBookReviews(book);

        return BookReviewDtoMapper.toDto(bookReview);
    }

    public void deleteBookReview(String bookReviewId) {
        BookReview bookReview = bookReviewRepository.findById(bookReviewId).orElseThrow(
                () -> new NotFoundException(String.format("Book review with id=%s not found", bookReviewId)));

        bookReviewRepository.deleteById(bookReviewId);
        updateRecentBookReviews(bookReview.getBook());
    }

    private void updateRecentBookReviews(Book book) {
        List<BookReview> resentReviews = bookReviewRepository.findAllByBookId(book.getId(),
                PageRequest.of(0, 10, Sort.by("createdAt").descending()));

        book.setRecentReviews(resentReviews);
        bookRepository.save(book);
    }
}
