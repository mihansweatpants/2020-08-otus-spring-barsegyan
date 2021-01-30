package ru.otus.spring.barsegyan.dto.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ChatDto {
    private final UUID id;
    private final String name;
    private final Collection<UserDto> members;
    private final LocalDateTime lastUpdateTime;
}
