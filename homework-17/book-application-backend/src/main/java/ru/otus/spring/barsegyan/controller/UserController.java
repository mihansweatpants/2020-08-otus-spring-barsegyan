package ru.otus.spring.barsegyan.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.barsegyan.domain.ApplicationUser;
import ru.otus.spring.barsegyan.dto.UserDto;
import ru.otus.spring.barsegyan.dto.mappers.UserDtoMapper;
import ru.otus.spring.barsegyan.dto.rest.ApiResponse;
import ru.otus.spring.barsegyan.type.ApplicationUserDetails;

@RestController
public class UserController {

    @RequestMapping(value = "/api/users/me", method = {RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<UserDto> getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof ApplicationUserDetails) {
            ApplicationUser user = ((ApplicationUserDetails) principal).getUserDetails();
            return ApiResponse.ok(UserDtoMapper.toDto(user));
        } else {
            throw new SecurityException("User not found");
        }
    }
}
