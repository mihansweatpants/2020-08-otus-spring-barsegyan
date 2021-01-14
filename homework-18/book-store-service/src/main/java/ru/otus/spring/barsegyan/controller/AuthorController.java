package ru.otus.spring.barsegyan.controller;

import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.barsegyan.domain.Author;
import ru.otus.spring.barsegyan.dto.AuthorDto;
import ru.otus.spring.barsegyan.dto.mappers.AuthorDtoMapper;
import ru.otus.spring.barsegyan.dto.rest.ApiResponse;
import ru.otus.spring.barsegyan.dto.rest.Pagination;
import ru.otus.spring.barsegyan.service.AuthorService;

import java.util.stream.Collectors;

@Api
@RestController
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/api/authors")
    public ApiResponse<Pagination<AuthorDto>> getAll(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int limit) {
        Page<Author> genresPage = authorService.getAllAuthors(PageRequest.of(page, limit));

        return ApiResponse.ok(Pagination.of(
                genresPage.stream().map(AuthorDtoMapper::toDto).collect(Collectors.toList()),
                genresPage.getTotalPages(),
                genresPage.getTotalElements()
        ));
    }
}
