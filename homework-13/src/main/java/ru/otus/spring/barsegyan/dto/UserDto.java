package ru.otus.spring.barsegyan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.barsegyan.domain.ApplicationUserRole;

@Data
@AllArgsConstructor
public class UserDto {
    private final Long id;
    private final String username;
    private final ApplicationUserRole role;
    private final Boolean isBlocked;
}
