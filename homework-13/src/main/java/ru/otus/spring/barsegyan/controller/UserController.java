package ru.otus.spring.barsegyan.controller;

import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.barsegyan.domain.ApplicationUser;
import ru.otus.spring.barsegyan.dto.CreateUserDto;
import ru.otus.spring.barsegyan.dto.UpdateUserDto;
import ru.otus.spring.barsegyan.dto.UserDto;
import ru.otus.spring.barsegyan.dto.mappers.UserDtoMapper;
import ru.otus.spring.barsegyan.dto.rest.ApiResponse;
import ru.otus.spring.barsegyan.dto.rest.Pagination;
import ru.otus.spring.barsegyan.service.ApplicationUserService;
import ru.otus.spring.barsegyan.type.ApplicationUserDetails;

import java.util.stream.Collectors;

@RestController
@Api
public class UserController {

    private final ApplicationUserService applicationUserService;

    public UserController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @GetMapping("/api/users/me")
    public ApiResponse<UserDto> getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof ApplicationUserDetails) {
            ApplicationUserDetails userDetails = ((ApplicationUserDetails) principal);

            return ApiResponse.ok(new UserDto(
                    userDetails.getUserId(),
                    userDetails.getUsername(),
                    userDetails.getRole(),
                    userDetails.isAccountNonLocked()));
        } else {
            throw new SecurityException("User not found");
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/api/users")
    public ApiResponse<UserDto> createUser(@RequestBody CreateUserDto createUserDto) {
        ApplicationUser createdUser = applicationUserService.createUser(createUserDto);

        return ApiResponse.ok(UserDtoMapper.toDto(createdUser));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') || authentication.principal.getUserId().equals(#userId)")
    @PatchMapping("/api/users/{userId}")
    public ApiResponse<UserDto> updateUser(@PathVariable Long userId, @RequestBody UpdateUserDto updateUserDto) {
        ApplicationUser updatedUser = applicationUserService.updateUserById(userId, updateUserDto);

        return ApiResponse.ok(UserDtoMapper.toDto(updatedUser));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/api/users")
    public ApiResponse<Pagination<UserDto>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int limit) {
        Page<ApplicationUser> usersPage = applicationUserService.getAllUsers(PageRequest.of(page, limit));

        return ApiResponse.ok(
                Pagination.of(
                        usersPage.stream().map(UserDtoMapper::toDto).collect(Collectors.toList()),
                        usersPage.getTotalPages(),
                        usersPage.getTotalElements()
                )
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/api/users/{userId}/block")
    public ApiResponse<Void> blockUser(@PathVariable Long userId) {
        applicationUserService.blockUser(userId);

        return ApiResponse.ok();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/api/users/{userId}/unblock")
    public ApiResponse<Void> unblockUser(@PathVariable Long userId) {
        applicationUserService.unblockUser(userId);

        return ApiResponse.ok();
    }
}
