package ru.otus.spring.barsegyan.dto.rest;

public class ApiError {

    private final String description;

    public ApiError(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}