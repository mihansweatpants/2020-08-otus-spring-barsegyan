package ru.otus.spring.barsegyan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UpdateBookDto {
    private final String title;
    private final String authorId;
    private final List<String> genreIds;
}
