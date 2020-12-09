package ru.otus.spring.barsegyan.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.barsegyan.domain.ApplicationUser;
import ru.otus.spring.barsegyan.exception.NotFoundException;
import ru.otus.spring.barsegyan.repository.ApplicationUserRepository;

@Service
public class ApplicationUserService {

    private final ApplicationUserRepository applicationUserRepository;

    public ApplicationUserService(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Transactional(readOnly = true)
    public ApplicationUser getByUsername(String username) {
        return applicationUserRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(String.format("User %s not found", username)));
    }
}
