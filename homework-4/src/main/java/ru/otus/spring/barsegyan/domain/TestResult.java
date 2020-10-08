package ru.otus.spring.barsegyan.domain;

public class TestResult {
    private int answersCount;
    private int correctAnswersCount;

    public TestResult() {
        this.answersCount = 0;
        this.correctAnswersCount = 0;
    }

    public int getCorrectAnswersCount() {
        return correctAnswersCount;
    }

    public int getAnswersCount() {
        return answersCount;
    }

    public void incrementCorrectAnswersCount() {
        correctAnswersCount++;
    }

    public void incrementAnswersCount() {
        answersCount++;
    }
}
