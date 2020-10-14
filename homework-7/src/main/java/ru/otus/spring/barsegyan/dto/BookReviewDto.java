package ru.otus.spring.barsegyan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookReviewDto {
    private final long id;
    private final String text;
    private final String bookTitle;
}
