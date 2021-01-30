package ru.otus.spring.barsegyan.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.barsegyan.domain.Chat;
import ru.otus.spring.barsegyan.dto.rest.base.ApiResponse;
import ru.otus.spring.barsegyan.dto.rest.mappers.ChatDtoMapper;
import ru.otus.spring.barsegyan.dto.rest.request.CreateChatDto;
import ru.otus.spring.barsegyan.dto.rest.request.UpdateChatDto;
import ru.otus.spring.barsegyan.dto.rest.response.ChatDto;
import ru.otus.spring.barsegyan.dto.rest.response.UserDto;
import ru.otus.spring.barsegyan.service.ChatService;
import ru.otus.spring.barsegyan.service.SessionService;
import ru.otus.spring.barsegyan.type.AppUserDetails;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Api
@RestController
public class ChatController {

    private final ChatService chatService;
    private final SessionService sessionService;

    public ChatController(ChatService chatService,
                          SessionService sessionService) {
        this.chatService = chatService;
        this.sessionService = sessionService;
    }

    @GetMapping("/api/chats/{chatId}")
    public ApiResponse<ChatDto> getById(@PathVariable UUID chatId) {
        Chat chat = chatService.getById(chatId);

        return ApiResponse.ok(ChatDtoMapper.map(chat, getMembersDto(chat)));
    }

    @GetMapping
    public ApiResponse<List<ChatDto>> getAllUserChats() {
        AppUserDetails currentUser = sessionService.getCurrentUser();

        List<ChatDto> chats = chatService.getChatsByMemberId(currentUser.getUserId())
                .stream()
                .map(chat -> ChatDtoMapper.map(chat, getMembersDto(chat)))
                .collect(Collectors.toList());

        return ApiResponse.ok(chats);
    }

    @PostMapping("/api/chats")
    public ApiResponse<ChatDto> create(@RequestBody @Valid CreateChatDto createChatDto) {
        Chat chat = chatService.createChat(createChatDto);

        return ApiResponse.ok(ChatDtoMapper.map(chat, getMembersDto(chat)));
    }

    @PatchMapping("/api/chats/{chatId}")
    public ApiResponse<ChatDto> update(@PathVariable UUID chatId, @RequestBody UpdateChatDto updateChatDto) {
        Chat chat = chatService.updateChatById(chatId, updateChatDto);

        return ApiResponse.ok(ChatDtoMapper.map(chat, getMembersDto(chat)));
    }

    private List<UserDto> getMembersDto(Chat chat) {
        return sessionService.mapOnlineStatus(chat.getMembers());
    }
}
