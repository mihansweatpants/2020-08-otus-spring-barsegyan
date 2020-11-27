package ru.otus.spring.barsegyan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.spring.barsegyan.dto.AuthorDto;
import ru.otus.spring.barsegyan.dto.mappers.AuthorDtoMapper;
import ru.otus.spring.barsegyan.repository.AuthorRepository;

@RestController
public class AuthorController {

    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("/api/authors")
    public Flux<AuthorDto> getAll() {
        return authorRepository.findAll().map(AuthorDtoMapper::toDto);
    }
}
