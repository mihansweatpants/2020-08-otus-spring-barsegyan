package ru.otus.spring.barsegyan.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.barsegyan.domain.AppUser;
import ru.otus.spring.barsegyan.domain.Chat;
import ru.otus.spring.barsegyan.dto.rest.request.CreateChatDto;
import ru.otus.spring.barsegyan.dto.rest.request.UpdateChatDto;
import ru.otus.spring.barsegyan.repository.ChatRepository;
import ru.otus.spring.barsegyan.repository.UserRepository;
import ru.otus.spring.barsegyan.util.UTCTimeUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public ChatService(ChatRepository chatRepository,
                       UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public Chat getById(UUID chatId) {
        return chatRepository.findById(chatId).orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<Chat> getChatsByMemberId(UUID memberId) {
        return chatRepository.findAllByMembers_Id(memberId);
    }

    @Transactional
    public Chat createChat(CreateChatDto createChatDto) {
        Chat chat = new Chat()
                .setName(createChatDto.getChatName())
                .setMembers(userRepository.findAllByIdIn(createChatDto.getMemberIds()))
                .setLastUpdateTime(UTCTimeUtils.now());

        return chatRepository.save(chat);
    }

    @Transactional
    public Chat updateChatById(UUID chatId, UpdateChatDto updateChatDto) {
        Chat chat = chatRepository.findById(chatId).orElseThrow();

        Optional.ofNullable(updateChatDto.getName()).ifPresent(chat::setName);
        chat.setLastUpdateTime(UTCTimeUtils.now());

        return chatRepository.save(chat);
    }

    @Transactional
    public Chat addMembers(UUID chatId, List<UUID> userIds) {
        Chat chat = chatRepository.findById(chatId).orElseThrow();

        // TODO notify about added users
        List<AppUser> newMembers = userRepository.findAllByIdIn(userIds);
        chat.addMembers(newMembers);
        chat.setLastUpdateTime(UTCTimeUtils.now());

        return chatRepository.save(chat);
    }

    @Transactional
    public Chat removeMembers(UUID chatId, List<UUID> userIds) {
        Chat chat = chatRepository.findById(chatId).orElseThrow();

        // TODO notify about removed users
        List<AppUser> updatedMembers = chat.getMembers()
                .stream()
                .filter(member -> !userIds.contains(member.getId()))
                .collect(Collectors.toList());

        chat
                .setMembers(updatedMembers)
                .setLastUpdateTime(UTCTimeUtils.now());

        return chatRepository.save(chat);
    }
}
