package ru.otus.spring.barsegyan.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.otus.spring.barsegyan.dto.BookDto;
import ru.otus.spring.barsegyan.dto.CreateBookDto;
import ru.otus.spring.barsegyan.dto.rest.ApiResponse;
import ru.otus.spring.barsegyan.dto.rest.Pagination;
import ru.otus.spring.barsegyan.repository.BookRepository;
import ru.otus.spring.barsegyan.util.TestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestUtils testUtils;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void createBook() throws Exception {
        var createBookDtoString = objectMapper.writeValueAsString(new CreateBookDto("Test book", 1L, List.of(1L, 2L)));

        MvcResult result = mockMvc.perform(post("/api/books")
                .contentType("application/json")
                .content(createBookDtoString))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<BookDto> response = testUtils.parseResponse(result, new TypeReference<ApiResponse<BookDto>>() {});

        var actualBook = bookRepository.findById(response.getData().getId());

        assertThat(actualBook).isPresent().get()
                .matches(book -> book.getId().equals(response.getData().getId()))
                .matches(book -> book.getTitle().equals(response.getData().getTitle()))
                .matches(book -> book.getAuthor().getId().equals(response.getData().getAuthor().getId()))
                .matches(book -> book.getGenres()
                        .stream()
                        .allMatch(genre -> response.getData().getGenres()
                                .stream()
                                .anyMatch(genreDto -> genreDto.getId().equals(genre.getId()))));
    }

    @Test
    @WithMockUser
    public void getById() throws Exception {
        var firstBookId = 1L;

        MvcResult result = mockMvc.perform(get("/api/books/{bookId}", firstBookId))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<BookDto> response = testUtils.parseResponse(result, new TypeReference<ApiResponse<BookDto>>() {});

        var actualBook = bookRepository.findById(firstBookId);

        assertThat(actualBook).isPresent().get()
                .matches(book -> book.getId().equals(response.getData().getId()))
                .matches(book -> book.getTitle().equals(response.getData().getTitle()))
                .matches(book -> book.getAuthor().getId().equals(response.getData().getAuthor().getId()))
                .matches(book -> book.getGenres()
                        .stream()
                        .allMatch(genre -> response.getData().getGenres()
                                .stream()
                                .anyMatch(genreDto -> genreDto.getId().equals(genre.getId()))));
    }

    @Test
    @WithMockUser
    public void getBooksList() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<Pagination<BookDto>> response = testUtils.parseResponse(result, new TypeReference<ApiResponse<Pagination<BookDto>>>() {});

        var actualBooks = bookRepository.findAll(PageRequest.of(0, 10));

        assertThat(response.getData().getItems())
                .allMatch(bookDto -> actualBooks.stream().anyMatch(book -> book.getId().equals(bookDto.getId())));
    }
}