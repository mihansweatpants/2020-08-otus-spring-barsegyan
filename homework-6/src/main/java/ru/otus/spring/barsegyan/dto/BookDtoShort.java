package ru.otus.spring.barsegyan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BookDtoShort {
    private final long id;
    private final String title;
    private final AuthorDto author;
    private final List<GenreDto> genres;
}
