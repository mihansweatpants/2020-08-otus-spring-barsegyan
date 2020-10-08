package ru.otus.spring.barsegyan.service;

import ru.otus.spring.barsegyan.domain.Question;
import ru.otus.spring.barsegyan.domain.Student;
import ru.otus.spring.barsegyan.domain.TestResult;
import ru.otus.spring.barsegyan.exception.TestingFinishedException;

public interface TestingService {
    void startTesting(Student student);

    Question getCurrentQuestion(Student student) throws TestingFinishedException;

    void saveAnswer(String answer, Student student) throws TestingFinishedException;

    TestResult getTestResult(Student student);
}
