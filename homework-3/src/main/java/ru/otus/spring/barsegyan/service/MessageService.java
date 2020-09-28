package ru.otus.spring.barsegyan.service;

public interface MessageService {
    String getLocalizedMessage(String code);
    String getLocalizedMessage(String code, Object[] params);
}
