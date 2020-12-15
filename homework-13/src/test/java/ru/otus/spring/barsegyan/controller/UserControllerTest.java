package ru.otus.spring.barsegyan.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.otus.spring.barsegyan.domain.ApplicationUser;
import ru.otus.spring.barsegyan.domain.ApplicationUserRole;
import ru.otus.spring.barsegyan.dto.CreateUserDto;
import ru.otus.spring.barsegyan.dto.UpdateUserDto;
import ru.otus.spring.barsegyan.dto.UserDto;
import ru.otus.spring.barsegyan.dto.rest.ApiResponse;
import ru.otus.spring.barsegyan.dto.rest.Pagination;
import ru.otus.spring.barsegyan.repository.ApplicationUserRepository;
import ru.otus.spring.barsegyan.type.ApplicationUserDetails;
import ru.otus.spring.barsegyan.util.TestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private static final String ADMIN_USERNAME = "admin";
    private static final String REGULAR_USER_USERNAME = "test_user";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestUtils testUtils;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private ObjectMapper objectMapper;

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

    @Test
    public void testGetUsersListUnauthorized() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(ADMIN_USERNAME)
    public void testGetUsersListAuthorized() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<Pagination<UserDto>> response = testUtils.parseResponse(result, new TypeReference<ApiResponse<Pagination<UserDto>>>() {});

        var actualUsers = applicationUserRepository.findAll(PageRequest.of(0, 10));

        assertThat(response.getData().getItems())
                .allMatch(userDto -> actualUsers.stream().anyMatch(user -> user.getId().equals(userDto.getId())));
    }

    @Test
    public void testCreateUserAndLogin() throws Exception {
        var newUserDto = new CreateUserDto("new user", "qwerty", ApplicationUserRole.ROLE_USER);
        var createUserDtoString = objectMapper.writeValueAsString(newUserDto);

        var admin = applicationUserRepository.findByUsername(ADMIN_USERNAME);

        MvcResult result = mockMvc.perform(post("/api/users")
                .with(SecurityMockMvcRequestPostProcessors.user(new ApplicationUserDetails(admin.get())))
                .contentType("application/json")
                .content(createUserDtoString))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<UserDto> response = testUtils.parseResponse(result, new TypeReference<ApiResponse<UserDto>>() {});

        var actualCreatedUser = applicationUserRepository.findById(response.getData().getId());

        assertThat(actualCreatedUser).isPresent().get()
                .matches(user -> user.getId().equals(response.getData().getId()))
                .matches(user -> user.getUsername().equals(response.getData().getUsername()))
                .matches(user -> user.getRole().equals(response.getData().getRole()));

        mockMvc.perform(get("/api/users/me")
                .with(SecurityMockMvcRequestPostProcessors.user(new ApplicationUserDetails(actualCreatedUser.get())))
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateUserAndLoginWithNewCredentials() throws Exception {
        String updatedPassword = "new password";
        String updatedUsername = "new username";
        Long userId = 2L;
        var updateUserDto = new UpdateUserDto(updatedUsername, updatedPassword);
        var updateUserDtoString = objectMapper.writeValueAsString(updateUserDto);

        var regularUser = applicationUserRepository.findById(userId);

        MvcResult result = mockMvc.perform(patch("/api/users/{userId}", userId)
                .with(SecurityMockMvcRequestPostProcessors.user(new ApplicationUserDetails(regularUser.get())))
                .contentType("application/json")
                .content(updateUserDtoString))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<UserDto> response = testUtils.parseResponse(result, new TypeReference<ApiResponse<UserDto>>() {});

        var actualUpdatedUser = applicationUserRepository.findById(response.getData().getId());

        assertThat(actualUpdatedUser).isPresent().get()
                .matches(user -> user.getId().equals(response.getData().getId()))
                .matches(user -> user.getUsername().equals(response.getData().getUsername()))
                .matches(user -> user.getRole().equals(response.getData().getRole()));

        mockMvc.perform(get("/api/users/me")
                .with(SecurityMockMvcRequestPostProcessors.user(new ApplicationUserDetails(actualUpdatedUser.get())))
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void testBlockUser() throws Exception {
        Long userToBlockId = 2L;

        var admin = applicationUserRepository.findByUsername(ADMIN_USERNAME);

        mockMvc.perform(post("/api/users/{userId}/block", userToBlockId)
                .with(SecurityMockMvcRequestPostProcessors.user(new ApplicationUserDetails(admin.get())))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        var actualBlockedUser = applicationUserRepository.findById(userToBlockId);

        assertThat(actualBlockedUser).isPresent().get()
                .matches(ApplicationUser::getIsBlocked, "User is blocked");

        mockMvc.perform(get("/api/users/me")
                .with(SecurityMockMvcRequestPostProcessors.user(new ApplicationUserDetails(actualBlockedUser.get()))))
                .andExpect(status().isUnauthorized());
    }
}
