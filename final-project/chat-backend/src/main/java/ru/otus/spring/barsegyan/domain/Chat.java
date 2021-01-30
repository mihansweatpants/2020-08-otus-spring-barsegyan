package ru.otus.spring.barsegyan.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "chat")
public class Chat {
    @Id
    @GeneratedValue
    @Column(name = "chat_id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(name = "app_user_chat",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns =@JoinColumn(name = "app_user_id"))
    private Set<AppUser> members;

    @Column(name = "last_update_time")
    private LocalDateTime lastUpdateTime;

    public Chat () {}

    public Chat(UUID id,
                String name,
                Set<AppUser> members,
                LocalDateTime lastUpdateTime) {
        this.id = id;
        this.name = name;
        this.members = members;
        this.lastUpdateTime = lastUpdateTime;
    }

    public Chat setName(String name) {
        this.name = name;
        return this;
    }

    public Chat setMembers(Set<AppUser> members) {
        this.members = members;
        return this;
    }

    public Chat setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
        return this;
    }

    public Chat addMembers(Set<AppUser> newMembers) {
        members.addAll(newMembers);
        return this;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<AppUser> getMembers() {
        return members;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }
}
