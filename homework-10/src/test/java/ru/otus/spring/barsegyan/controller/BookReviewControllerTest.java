package ru.otus.spring.barsegyan.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.otus.spring.barsegyan.dto.BookReviewDto;
import ru.otus.spring.barsegyan.dto.rest.ApiResponse;
import ru.otus.spring.barsegyan.dto.rest.Pagination;
import ru.otus.spring.barsegyan.repository.BookReviewRepository;
import ru.otus.spring.barsegyan.util.TestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestUtils testUtils;

    @Autowired
    private BookReviewRepository bookReviewRepository;

    @Test
    public void getReviews() throws Exception {
        var firstBookId = 1L;

        MvcResult result = mockMvc.perform(get("/api/books/{bookId}/reviews", firstBookId))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<Pagination<BookReviewDto>> response = testUtils.parseResponse(result, new TypeReference<ApiResponse<Pagination<BookReviewDto>>>() {});

        var actualBookReviewsPage = bookReviewRepository.findAllByBookId(firstBookId,
                PageRequest.of(0, 10, Sort.by("createdAt").descending()));

        assertThat(response.getData().getTotalItems()).isEqualTo(actualBookReviewsPage.getTotalElements());
        assertThat(response.getData().getTotalPages()).isEqualTo(actualBookReviewsPage.getTotalPages());
        assertThat(response.getData().getItems())
                .allMatch(bookReviewDto -> actualBookReviewsPage
                        .stream()
                        .anyMatch(bookReview -> bookReview.getId().equals(bookReviewDto.getId())));
    }
}