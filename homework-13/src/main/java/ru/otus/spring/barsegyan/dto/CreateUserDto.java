package ru.otus.spring.barsegyan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.barsegyan.domain.ApplicationUserRole;

@Data
@AllArgsConstructor
public class CreateUserDto {
    private final String username;
    private final String password;
    private final ApplicationUserRole applicationUserRole;
}
