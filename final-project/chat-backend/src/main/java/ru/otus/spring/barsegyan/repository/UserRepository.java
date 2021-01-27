package ru.otus.spring.barsegyan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.barsegyan.domain.AppUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<AppUser, UUID> {
    Optional<AppUser> findByUsername(String username);
    List<AppUser> findAllByIdIn(List<UUID> userIds);
}
