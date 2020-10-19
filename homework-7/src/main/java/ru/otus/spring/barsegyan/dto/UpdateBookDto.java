package ru.otus.spring.barsegyan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UpdateBookDto {
    private final long bookId;
    private final String title;
    private final long authorId;
    private final List<Long> genreIds;
}
