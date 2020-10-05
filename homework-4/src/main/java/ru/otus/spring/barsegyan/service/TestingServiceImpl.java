package ru.otus.spring.barsegyan.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.barsegyan.dao.TestResultDao;
import ru.otus.spring.barsegyan.domain.Question;
import ru.otus.spring.barsegyan.domain.Student;
import ru.otus.spring.barsegyan.domain.TestResult;
import ru.otus.spring.barsegyan.exception.TestingFinishedException;

@Service
public class TestingServiceImpl implements TestingService {

    private final QuestionService questionService;
    private final TestResultDao testResultDao;

    public TestingServiceImpl(QuestionService questionService, TestResultDao testResultDao) {
        this.questionService = questionService;
        this.testResultDao = testResultDao;
    }

    public void startTesting(Student student) {
        testResultDao.saveTestResult(new TestResult(), student);
    }

    public Question getCurrentQuestion(Student student) throws TestingFinishedException {
        if (isTestingFinished(getTestResult(student))) {
            throw new TestingFinishedException("Testing finished. No questions left.");
        }

        TestResult testResult = getTestResult(student);

        return questionService.getQuestionByNumber(testResult.getAnswersCount());
    }

    public void saveAnswer(String answer, Student student) throws TestingFinishedException {
        TestResult testResult = getTestResult(student);

        if (isTestingFinished(testResult)) {
            throw new TestingFinishedException("Testing finished. All questions are answered");
        }

        if (questionService.getQuestionByNumber(testResult.getAnswersCount()).getCorrectAnswerNumber().equals(answer)) {
            testResult.incrementCorrectAnswersCount();
        }

        testResult.incrementAnswersCount();

        testResultDao.saveTestResult(testResult, student);
    }

    public TestResult getTestResult(Student student) {
        return testResultDao.findByStudent(student);
    }

    private boolean isTestingFinished(TestResult testResult) {
        return testResult.getAnswersCount() == questionService.getTotalQuestions();
    }
}
