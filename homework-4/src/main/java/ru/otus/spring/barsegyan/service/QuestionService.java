package ru.otus.spring.barsegyan.service;

import ru.otus.spring.barsegyan.domain.Question;

public interface QuestionService {
    Question getQuestionByNumber(int questionNumber);

    int getTotalQuestions();
}
