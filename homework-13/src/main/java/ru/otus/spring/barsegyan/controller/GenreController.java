package ru.otus.spring.barsegyan.controller;

import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.barsegyan.domain.Genre;
import ru.otus.spring.barsegyan.dto.GenreDto;
import ru.otus.spring.barsegyan.dto.mappers.GenreDtoMapper;
import ru.otus.spring.barsegyan.dto.rest.ApiResponse;
import ru.otus.spring.barsegyan.dto.rest.Pagination;
import ru.otus.spring.barsegyan.service.GenreService;

import java.util.stream.Collectors;

@RestController
@Api
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/api/genres")
    public ApiResponse<Pagination<GenreDto>> getAll(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int limit) {
        Page<Genre> genresPage = genreService.getAllGenres(PageRequest.of(page, limit));

        return ApiResponse.ok(Pagination.of(
                genresPage.stream().map(GenreDtoMapper::toDto).collect(Collectors.toList()),
                genresPage.getTotalPages(),
                genresPage.getTotalElements()
        ));
    }
}
