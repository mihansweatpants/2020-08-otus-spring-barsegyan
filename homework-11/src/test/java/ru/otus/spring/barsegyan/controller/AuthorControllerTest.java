package ru.otus.spring.barsegyan.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.otus.spring.barsegyan.domain.Author;
import ru.otus.spring.barsegyan.dto.AuthorDto;
import ru.otus.spring.barsegyan.repository.AuthorRepository;

import java.util.List;


@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = AuthorController.class)
class AuthorControllerTest {
    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private WebTestClient webClient;

    @Test
    void getAuthors() {
        List<Author> authors = List.of(new Author("1", "Tolstoy"), new Author("2", "Pushkin"));
        Flux<Author> authorFlux = Flux.fromIterable(authors);
        Mockito.when(authorRepository.findAll()).thenReturn(authorFlux);

        webClient
                .get()
                .uri("/api/authors")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AuthorDto.class);

        Mockito.verify(authorRepository).findAll();
    }
}