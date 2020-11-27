package ru.otus.spring.barsegyan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.spring.barsegyan.dto.GenreDto;
import ru.otus.spring.barsegyan.dto.mappers.GenreDtoMapper;
import ru.otus.spring.barsegyan.repository.GenreRepository;

@RestController
public class GenreController {

    private final GenreRepository genreRepository;

    public GenreController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @GetMapping("/api/genres")
    public Flux<GenreDto> getAll() {
        return genreRepository.findAll().map(GenreDtoMapper::toDto);
    }
}
