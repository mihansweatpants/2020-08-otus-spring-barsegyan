package ru.otus.spring.barsegyan.dto.mappers;

import ru.otus.spring.barsegyan.domain.Book;
import ru.otus.spring.barsegyan.dto.BookDto;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookDtoMapper {
    public static BookDto toDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                AuthorDtoMapper.toDto(book.getAuthor()),
                book.getGenres()
                        .stream()
                        .map(GenreDtoMapper::toDto)
                        .collect(Collectors.toList()),
                Optional.ofNullable(book.getRecentReviews())
                        .map(bookReviews -> bookReviews
                                .stream()
                                .map(BookReviewDtoMapper::toDto)
                                .collect(Collectors.toList()))
                        .orElse(Collections.emptyList()));
    }
}
