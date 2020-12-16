package ru.otus.spring.barsegyan.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.barsegyan.domain.Book;
import ru.otus.spring.barsegyan.domain.BookReview;
import ru.otus.spring.barsegyan.dto.CreateBookReviewDto;
import ru.otus.spring.barsegyan.repository.BookReviewRepository;

import java.time.LocalDateTime;

@Service
public class BookReviewService {

    private final BookService bookService;
    private final BookReviewRepository bookReviewRepository;

    public BookReviewService(BookReviewRepository bookReviewRepository,
                             BookService bookService) {
        this.bookReviewRepository = bookReviewRepository;
        this.bookService = bookService;
    }

    @Transactional(readOnly = true)
    public Page<BookReview> getAllReviewsByBookId(long bookId, Pageable pageable) {
        return bookReviewRepository.findAllByBookId(bookId, pageable);
    }

    @Transactional
    public BookReview createBookReview(CreateBookReviewDto createBookReviewDto) {
        Book book = bookService.getBookById(createBookReviewDto.getBookId());

        BookReview bookReview = new BookReview();
        bookReview.setBook(book);
        bookReview.setText(createBookReviewDto.getText());
        bookReview.setCreatedAt(LocalDateTime.now());

        return bookReviewRepository.save(bookReview);
    }

    @Transactional
    public void deleteBookReview(long bookReviewId) {
        bookReviewRepository.deleteById(bookReviewId);
    }
}
