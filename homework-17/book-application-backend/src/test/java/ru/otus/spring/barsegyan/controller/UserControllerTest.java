package ru.otus.spring.barsegyan.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.otus.spring.barsegyan.dto.UserDto;
import ru.otus.spring.barsegyan.dto.rest.ApiResponse;
import ru.otus.spring.barsegyan.repository.ApplicationUserRepository;
import ru.otus.spring.barsegyan.util.TestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private static final String ADMIN_USERNAME = "admin";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestUtils testUtils;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Test
    public void testGetCurrentUserUnauthenticated() throws Exception {
        mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(ADMIN_USERNAME)
    public void testGetCurrentUserAuthenticated() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<UserDto> response = testUtils.parseResponse(result, new TypeReference<ApiResponse<UserDto>>() {});

        var actualUser = applicationUserRepository.findByUsername(ADMIN_USERNAME);

        assertThat(actualUser).isPresent().get()
                .matches(user -> user.getId().equals(response.getData().getId()))
                .matches(user -> user.getUsername().equals(response.getData().getUsername()));
    }
}
