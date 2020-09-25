package ru.otus.spring.barsegyan.service;

import ru.otus.spring.barsegyan.domain.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getQuestions();
}
