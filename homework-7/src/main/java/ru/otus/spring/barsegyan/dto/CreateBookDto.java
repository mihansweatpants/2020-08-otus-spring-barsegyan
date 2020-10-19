package ru.otus.spring.barsegyan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CreateBookDto {
    private final String title;
    private final long authorId;
    private List<Long> genreIds;
}
