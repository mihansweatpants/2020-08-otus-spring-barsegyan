package ru.otus.spring.barsegyan.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.spring.barsegyan.dao.TestResultDaoImpl;
import ru.otus.spring.barsegyan.domain.Question;
import ru.otus.spring.barsegyan.domain.Student;
import ru.otus.spring.barsegyan.domain.TestResult;
import ru.otus.spring.barsegyan.exception.TestingFinishedException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(
        properties = {
                InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
                ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
        }
)
@DisplayName("Testing service")
public class TestingServiceTest {
    @Mock
    private QuestionServiceImpl questionService;

    @Mock
    private TestResultDaoImpl testResultDao;

    @InjectMocks
    private TestingServiceImpl testingService;

    private Student testedStudent;

    @BeforeEach
    void setUp() {
        testedStudent = new Student("Sponge", "Bob");
        testingService.startTesting(testedStudent);
    }

    @DisplayName("starts testing and returns first question")
    @Test
    void startsTestingAndReturnsFirstQuestion() {
        when(questionService.getQuestionByNumber(Mockito.anyInt())).thenReturn(
                new Question("Sample question", "1", List.of("1) First option", "2) Second option", "3) Third option"))
        );

        when(questionService.getTotalQuestions()).thenReturn(1);

        when(testResultDao.findByStudent(Mockito.any(Student.class))).thenReturn(new TestResult());

        Question expected = questionService.getQuestionByNumber(0);
        Question actual = testingService.getCurrentQuestion(testedStudent);

        assertEquals(expected, actual);
    }

    @DisplayName("throws TestingFinishedException when all questions are answered")
    @Test
    void throwsTestingFinishedException() {
        when(questionService.getTotalQuestions()).thenReturn(1);
        TestResult mockedResult = new TestResult();
        mockedResult.incrementAnswersCount();
        when(testResultDao.findByStudent(Mockito.any(Student.class))).thenReturn(mockedResult);

        assertThrows(TestingFinishedException.class, () -> testingService.getCurrentQuestion(testedStudent));
        assertThrows(TestingFinishedException.class, () -> testingService.saveAnswer(Mockito.anyString(), testedStudent));
    }
}
