package ru.otus.spring.barsegyan.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.barsegyan.dto.GenreDto;
import ru.otus.spring.barsegyan.dto.mappers.GenreDtoMapper;
import ru.otus.spring.barsegyan.repository.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<GenreDto> getAllGenres() {
        return genreRepository.findAll()
                .stream()
                .map(GenreDtoMapper::toDto)
                .collect(Collectors.toList());
    }
}
