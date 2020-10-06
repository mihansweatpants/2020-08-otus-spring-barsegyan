package ru.otus.spring.barsegyan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateBookDto {
    private long id;
    private String title;
    private long authorId;
    private long genreId;
}
