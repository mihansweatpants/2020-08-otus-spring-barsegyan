package ru.otus.spring.barsegyan.dto.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ChatDto {
    private final String name;
    private final List<UserDto> members;
    private final LocalDateTime lastUpdateTime;
}
