package ru.otus.spring.barsegyan.service;

public interface MessageLocalizationService {
    String getLocalizedMessage(String code);
    String getLocalizedMessage(String code, Object[] params);
}
