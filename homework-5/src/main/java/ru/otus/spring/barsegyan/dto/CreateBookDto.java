package ru.otus.spring.barsegyan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateBookDto {
    private final String title;
    private final long authorId;
    private final long genreId;
}
