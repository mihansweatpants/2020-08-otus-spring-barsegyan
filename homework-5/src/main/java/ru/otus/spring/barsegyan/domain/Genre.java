package ru.otus.spring.barsegyan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Genre {
    private final long id;
    private String name;
}
