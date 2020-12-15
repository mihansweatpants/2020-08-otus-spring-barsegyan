package ru.otus.spring.barsegyan.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.barsegyan.domain.ApplicationUser;
import ru.otus.spring.barsegyan.dto.CreateUserDto;
import ru.otus.spring.barsegyan.dto.UpdateUserDto;
import ru.otus.spring.barsegyan.exception.NotFoundException;
import ru.otus.spring.barsegyan.repository.ApplicationUserRepository;

import java.util.Optional;

@Service
public class ApplicationUserService {

    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;

    public ApplicationUserService(ApplicationUserRepository applicationUserRepository,
                                  PasswordEncoder passwordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public ApplicationUser getByUsername(String username) {
        return applicationUserRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(String.format("User %s not found", username)));
    }

    @Transactional(readOnly = true)
    public Page<ApplicationUser> getAllUsers(Pageable pageable) {
        return applicationUserRepository.findAll(pageable);
    }

    @Transactional
    public void blockUser(Long userId) {
        ApplicationUser userToBlock = applicationUserRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id=%s not found", userId)));

        userToBlock.setIsBlocked(true);
        applicationUserRepository.save(userToBlock);
    }

    @Transactional
    public void unblockUser(Long userId) {
        ApplicationUser userToUnblock = applicationUserRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id=%s not found", userId)));

        userToUnblock.setIsBlocked(false);
        applicationUserRepository.save(userToUnblock);
    }

    @Transactional
    public ApplicationUser updateUserById(long userId, UpdateUserDto updateUserDto) {
        ApplicationUser userToUpdate = applicationUserRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id=%s not found", userId)));

        Optional.ofNullable(updateUserDto.getUsername()).ifPresent(userToUpdate::setUsername);
        Optional.ofNullable(updateUserDto.getPassword()).map(passwordEncoder::encode).ifPresent(userToUpdate::setPassword);

        return applicationUserRepository.save(userToUpdate);
    }

    @Transactional
    public ApplicationUser createUser(CreateUserDto createUserDto) {
        ApplicationUser newUser = new ApplicationUser();
        newUser.setUsername(createUserDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        newUser.setRole(createUserDto.getApplicationUserRole());

        return applicationUserRepository.save(newUser);
    }
}
