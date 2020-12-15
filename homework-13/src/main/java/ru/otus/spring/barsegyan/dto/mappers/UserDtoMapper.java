package ru.otus.spring.barsegyan.dto.mappers;

import ru.otus.spring.barsegyan.domain.ApplicationUser;
import ru.otus.spring.barsegyan.dto.UserDto;

public class UserDtoMapper {
    public static UserDto toDto(ApplicationUser user) {
        return new UserDto(user.getId(), user.getUsername(), user.getRole(), user.getIsBlocked());
    }
}
