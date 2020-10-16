package ru.otus.spring.barsegyan.dto.mappers;

import ru.otus.spring.barsegyan.domain.Genre;
import ru.otus.spring.barsegyan.dto.GenreDto;

public class GenreDtoMapper {
    public static GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }
}
