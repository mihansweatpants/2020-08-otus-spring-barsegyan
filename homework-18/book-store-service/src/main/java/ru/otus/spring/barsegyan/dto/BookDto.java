package ru.otus.spring.barsegyan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class BookDto {
    private final Long id;
    private final String title;
    private final AuthorDto author;
    private final List<GenreDto> genres;
    private final BigDecimal price;
}
