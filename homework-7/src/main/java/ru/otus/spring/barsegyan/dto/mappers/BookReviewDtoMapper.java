package ru.otus.spring.barsegyan.dto.mappers;

import ru.otus.spring.barsegyan.domain.BookReview;
import ru.otus.spring.barsegyan.dto.BookReviewDto;

public class BookReviewDtoMapper {
    public static BookReviewDto toDto(BookReview bookReview) {
        return new BookReviewDto(bookReview.getId(), bookReview.getText(), bookReview.getBook().getTitle());
    }
}
