package ru.otus.spring.barsegyan.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.barsegyan.dto.AuthorDto;
import ru.otus.spring.barsegyan.service.AuthorService;

import java.util.stream.Collectors;

@ShellComponent
public class AuthorCommands {

    private final AuthorService authorService;

    public AuthorCommands(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ShellMethod(value = "Get all authors", key = "get-authors")
    public String getAll() {
        return authorService.getAllAuthors()
                .stream()
                .map(this::formatAuthor)
                .collect(Collectors.joining("\n"));
    }

    private String formatAuthor(AuthorDto authorDto) {
        return String.format("Author \"%s\" (id: %s)", authorDto.getName(), authorDto.getId());
    }
}
