package ru.otus.spring.barsegyan.domain;

import java.util.List;

public class Question {

    private final String questionText;
    private final List<String> answerOptions;

    public Question(String questionText, List<String> answerOptions) {
        this.questionText = questionText;
        this.answerOptions = answerOptions;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getAnswerOptions() {
        return answerOptions;
    }
}
