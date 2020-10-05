package ru.otus.spring.barsegyan.domain;

import java.util.UUID;

public class Student {
    private final UUID id = UUID.randomUUID();
    private final String firstName;
    private final String lastName;

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public UUID getId() {
        return id;
    }
}
