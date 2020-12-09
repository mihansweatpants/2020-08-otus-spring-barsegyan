package ru.otus.spring.barsegyan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookReviewDto {
    private final Long id;
    private final String text;
    private final LocalDateTime createdAt;
}
