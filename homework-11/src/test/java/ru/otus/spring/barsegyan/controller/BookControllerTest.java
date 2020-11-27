package ru.otus.spring.barsegyan.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.barsegyan.domain.Author;
import ru.otus.spring.barsegyan.domain.Book;
import ru.otus.spring.barsegyan.domain.Genre;
import ru.otus.spring.barsegyan.dto.AuthorDto;
import ru.otus.spring.barsegyan.dto.BookDto;
import ru.otus.spring.barsegyan.dto.CreateBookDto;
import ru.otus.spring.barsegyan.dto.UpdateBookDto;
import ru.otus.spring.barsegyan.dto.mappers.BookDtoMapper;
import ru.otus.spring.barsegyan.repository.AuthorRepository;
import ru.otus.spring.barsegyan.repository.BookRepository;
import ru.otus.spring.barsegyan.repository.GenreRepository;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = BookController.class)
public class BookControllerTest {
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private GenreRepository genreRepository;
    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private WebTestClient webClient;

    @Test
    void getBooks() {

        Mockito.when(bookRepository.findAll())
                .thenReturn(Flux.fromIterable(List.of(TestData.CRIME_AND_PUNISHMENT(), TestData.SOLARIS())));

        webClient
                .get()
                .uri("/api/books")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDto.class);

        Mockito.verify(bookRepository).findAll();
    }

    @Test
    void createBook() {
        CreateBookDto createBookDto = TestData.CREATE_BOOK_DTO();
        Mockito.when(genreRepository.findAllById(createBookDto.getGenreIds()))
                .thenReturn(Flux.just(TestData.CLASSIC()));
        Mockito.when(authorRepository.findById(createBookDto.getAuthorId()))
                .thenReturn(Mono.just(TestData.DOSTOEVSKY()));
        Mockito.when(bookRepository.save(TestData.NEW_BOOK()))
                .thenReturn(Mono.just(TestData.NEW_BOOK()));

        webClient.post().uri("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(createBookDto))
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .isEqualTo(BookDtoMapper.toDto(TestData.NEW_BOOK()));

        Mockito.verify(bookRepository)
                .save(TestData.NEW_BOOK());
    }

    @Test
    void updateBook() {
        UpdateBookDto updateBookDto = TestData.UPDATE_BOOK_DTO();
        Mockito.when(bookRepository.findById(TestData.NEW_BOOK().getId()))
                .thenReturn(Mono.just(TestData.NEW_BOOK()));
        Mockito.when(genreRepository.findAllById(updateBookDto.getGenreIds()))
                .thenReturn(Flux.just(TestData.CLASSIC(), TestData.NOVEL()));
        Mockito.when(bookRepository.save(TestData.UPDATED_BOOK()))
                .thenReturn(Mono.just(TestData.UPDATED_BOOK()));

        webClient.patch().uri("/api/books/3")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(updateBookDto))
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .isEqualTo(BookDtoMapper.toDto(TestData.UPDATED_BOOK()));

        Mockito.verify(bookRepository)
                .save(TestData.UPDATED_BOOK());
    }

    private static class TestData {
        public static Book CRIME_AND_PUNISHMENT() {
            return new Book("1", "Crime and punishment", DOSTOEVSKY(), List.of(CLASSIC(), CRIME(), NOVEL()), Collections.emptyList());
        }

        public static Book SOLARIS() {
            return new Book("2", "Solaris", S_LEM(), List.of(NOVEL(), SCI_FI()), Collections.emptyList());
        }

        public static Author DOSTOEVSKY() {
            return new Author("1", "Dostoevsky");
        }

        public static Author S_LEM() {
            return new Author("2", "Stanislav Lem");
        }

        public static Genre CLASSIC() {
            return new Genre("1", "Classic");
        }

        public static Genre NOVEL() {
            return new Genre("2", "Novel");
        }

        public static Genre CRIME() {
            return new Genre("3", "Crime");
        }

        public static Genre SCI_FI() {
            return new Genre("4", "Sci-fi");
        }

        public static CreateBookDto CREATE_BOOK_DTO() {
            return new CreateBookDto("Idiot", "1", List.of("1"));
        }

        public static Book NEW_BOOK() {
            return new Book()
                    .setId("3")
                    .setTitle("Idiot")
                    .setAuthor(TestData.DOSTOEVSKY())
                    .setGenres(List.of(TestData.CLASSIC()));
        }

        public static UpdateBookDto UPDATE_BOOK_DTO() {
            return new UpdateBookDto(null, null, List.of("1", "2"));
        }

        public static Book UPDATED_BOOK() {
            return new Book()
                    .setId("3")
                    .setTitle("Idiot")
                    .setAuthor(TestData.DOSTOEVSKY())
                    .setGenres(List.of(TestData.CLASSIC(), TestData.NOVEL()));
        }
    }
}
