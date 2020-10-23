package ru.otus.spring.barsegyan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BookDto {
    private final Long id;
    private final String title;
    private final AuthorDto author;
    private final List<GenreDto> genres;
}
