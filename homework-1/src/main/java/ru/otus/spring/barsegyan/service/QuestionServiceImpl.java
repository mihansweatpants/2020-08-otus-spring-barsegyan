package ru.otus.spring.barsegyan.service;

import ru.otus.spring.barsegyan.dao.QuestionDao;

public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao questionDao;

    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public void printQuestions() {
        questionDao.findAll().forEach(question -> {
            System.out.println(question.getQuestionText());
            question.getAnswerOptions().forEach(option -> System.out.printf("  %s\n", option));
            System.out.println();
        });
    }
}
