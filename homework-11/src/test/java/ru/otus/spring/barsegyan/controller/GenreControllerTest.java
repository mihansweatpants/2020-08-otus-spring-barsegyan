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
import ru.otus.spring.barsegyan.domain.Genre;
import ru.otus.spring.barsegyan.dto.GenreDto;
import ru.otus.spring.barsegyan.repository.GenreRepository;

import java.util.List;


@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = GenreController.class)
class GenreControllerTest {
    @MockBean
    private GenreRepository genreRepository;

    @Autowired
    private WebTestClient webClient;

    @Test
    void getGenres() {
        List<Genre> genres = List.of(new Genre("1", "Detective"), new Genre("2", "Fantasy"));
        Flux<Genre> genreFlux = Flux.fromIterable(genres);
        Mockito.when(genreRepository.findAll()).thenReturn(genreFlux);

        webClient
                .get()
                .uri("/api/genres")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(GenreDto.class);

        Mockito.verify(genreRepository).findAll();
    }
}