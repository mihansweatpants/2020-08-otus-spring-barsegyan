package ru.otus.spring.barsegyan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookReviewDto {
    private final String id;
    private final String text;
    private final String bookTitle;
    private final LocalDateTime createdAt;
}
