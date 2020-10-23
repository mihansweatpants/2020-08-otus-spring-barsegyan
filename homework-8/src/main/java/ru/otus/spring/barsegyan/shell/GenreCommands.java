package ru.otus.spring.barsegyan.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.barsegyan.dto.GenreDto;
import ru.otus.spring.barsegyan.service.GenreService;

import java.util.stream.Collectors;

@ShellComponent
public class GenreCommands {

    private final GenreService genreService;

    public GenreCommands(GenreService genreService) {
        this.genreService = genreService;
    }

    @ShellMethod(value = "Get all genres", key = "get-genres")
    public String getAll() {
        return genreService.getAllGenres()
                .stream()
                .map(this::formatGenre)
                .collect(Collectors.joining("\n"));
    }

    private String formatGenre(GenreDto genreDto) {
        return String.format("Genre \"%s\" (id: %s)", genreDto.getName(), genreDto.getId());
    }
}
