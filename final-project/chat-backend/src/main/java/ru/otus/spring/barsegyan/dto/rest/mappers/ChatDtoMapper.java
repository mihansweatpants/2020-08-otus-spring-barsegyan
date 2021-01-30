package ru.otus.spring.barsegyan.dto.rest.mappers;

import ru.otus.spring.barsegyan.domain.Chat;
import ru.otus.spring.barsegyan.dto.rest.response.ChatDto;
import ru.otus.spring.barsegyan.dto.rest.response.UserDto;

import java.util.List;

public class ChatDtoMapper {
    public static ChatDto map(Chat chat, List<UserDto> chatMembers) {
        return new ChatDto(
                chat.getId(),
                chat.getName(),
                chatMembers,
                chat.getLastUpdateTime()
        );
    }
}
