package ru.otus.spring.barsegyan.dao;

import org.springframework.stereotype.Service;
import ru.otus.spring.barsegyan.domain.Student;
import ru.otus.spring.barsegyan.domain.TestResult;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TestResultDaoImpl implements TestResultDao {
    private Map<UUID, TestResult> resultMap = new HashMap<>();

    public TestResult saveTestResult(TestResult testResult, Student student) {
        return resultMap.put(student.getId(), testResult);
    }

    public TestResult findByStudent(Student student) {
        return resultMap.get(student.getId());
    }
}
