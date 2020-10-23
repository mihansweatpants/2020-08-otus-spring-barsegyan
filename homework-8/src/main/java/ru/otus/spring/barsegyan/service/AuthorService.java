package ru.otus.spring.barsegyan.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.barsegyan.dto.AuthorDto;
import ru.otus.spring.barsegyan.dto.mappers.AuthorDtoMapper;
import ru.otus.spring.barsegyan.repository.AuthorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(AuthorDtoMapper::toDto)
                .collect(Collectors.toList());
    }
}
