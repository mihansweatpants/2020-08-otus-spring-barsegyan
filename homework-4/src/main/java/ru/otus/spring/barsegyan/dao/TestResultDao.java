package ru.otus.spring.barsegyan.dao;

import ru.otus.spring.barsegyan.domain.Student;
import ru.otus.spring.barsegyan.domain.TestResult;

public interface TestResultDao {
    TestResult saveTestResult(TestResult testResult, Student student);

    TestResult findByStudent(Student student);
}
