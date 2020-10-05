package ru.otus.spring.barsegyan.domain;

import java.util.List;

public class Question {

    private final String questionText;
    private final List<String> answerOptions;
    private final String correctAnswerNumber;

    public Question(String questionText, String correctAnswerNumber, List<String> answerOptions) {
        this.questionText = questionText;
        this.answerOptions = answerOptions;
        this.correctAnswerNumber = correctAnswerNumber;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getAnswerOptions() {
        return answerOptions;
    }

    public String getCorrectAnswerNumber() {
        return correctAnswerNumber;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(getQuestionText());

        getAnswerOptions().forEach(option -> {
            sb.append("\n\t").append(option);
        });

        return sb.toString();
    }
}
