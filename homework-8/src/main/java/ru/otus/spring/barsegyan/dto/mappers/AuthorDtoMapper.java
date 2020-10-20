package ru.otus.spring.barsegyan.dto.mappers;

import ru.otus.spring.barsegyan.domain.Author;
import ru.otus.spring.barsegyan.dto.AuthorDto;

public class AuthorDtoMapper {
    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getName());
    }
}
