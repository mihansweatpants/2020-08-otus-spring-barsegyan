package ru.otus.spring.barsegyan.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.barsegyan.domain.Book;
import ru.otus.spring.barsegyan.domain.BookReview;
import ru.otus.spring.barsegyan.dto.BookReviewDto;
import ru.otus.spring.barsegyan.dto.CreateBookReviewDto;
import ru.otus.spring.barsegyan.dto.mappers.BookReviewDtoMapper;
import ru.otus.spring.barsegyan.exception.NotFoundException;
import ru.otus.spring.barsegyan.repositories.BookRepository;
import ru.otus.spring.barsegyan.repositories.BookReviewRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookReviewService {

    private final BookRepository bookRepository;
    private final BookReviewRepository bookReviewRepository;

    public BookReviewService(BookReviewRepository bookReviewRepository,
                             BookRepository bookRepository) {
        this.bookReviewRepository = bookReviewRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    public List<BookReviewDto> getAllReviewsByBookId(long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException(String.format("Book with id=%s not found", bookId)));

        return book.getReviews()
                .stream()
                .map(BookReviewDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookReviewDto createBookReview(CreateBookReviewDto createBookReviewDto) {
        Book book = bookRepository.findById(createBookReviewDto.getBookId())
                .orElseThrow(() -> new NotFoundException(String.format("Book with id=%s not found", createBookReviewDto.getBookId())));

        BookReview bookReview = new BookReview();
        bookReview.setBook(book);
        bookReview.setText(createBookReviewDto.getText());

        return BookReviewDtoMapper.toDto(bookReviewRepository.save(bookReview));
    }

    @Transactional
    public void deleteBookReview(long bookReviewId) {
        bookReviewRepository.deleteById(bookReviewId);
    }
}
