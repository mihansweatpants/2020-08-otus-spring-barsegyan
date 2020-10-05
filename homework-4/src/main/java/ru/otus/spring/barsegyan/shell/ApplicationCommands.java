package ru.otus.spring.barsegyan.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.barsegyan.domain.Student;
import ru.otus.spring.barsegyan.domain.TestResult;
import ru.otus.spring.barsegyan.exception.TestingFinishedException;
import ru.otus.spring.barsegyan.service.MessageLocalizationService;
import ru.otus.spring.barsegyan.service.TestingService;

@ShellComponent
public class ApplicationCommands {

    private final TestingService testingService;
    private final MessageLocalizationService messageLocalizationService;

    private Student testedStudent;

    public ApplicationCommands(TestingService testingService,
                               MessageLocalizationService messageLocalizationService) {
        this.testingService = testingService;
        this.messageLocalizationService = messageLocalizationService;
    }

    @ShellMethod(key = "start-testing", value = "Start testing")
    public String login(@ShellOption({"firstName", "f"}) String firstName,
                        @ShellOption({"lastName", "l"}) String lastName) {
        testedStudent = new Student(firstName, lastName);
        testingService.startTesting(testedStudent);

        return messageLocalizationService.getLocalizedMessage("testing-started", new Object[]{testedStudent.getFirstName(), testedStudent.getLastName()});
    }

    @ShellMethod(key = "get-question", value = "Get current question")
    @ShellMethodAvailability(value = "isTestingStarted")
    public String getQuestion() {
        try {
            return testingService.getCurrentQuestion(testedStudent).toString();
        } catch (TestingFinishedException e) {
            return messageLocalizationService.getLocalizedMessage("testing-finished", new Object[]{"get-result"});
        }
    }

    @ShellMethod(key = "answer", value = "Answer current question")
    @ShellMethodAvailability(value = "isTestingStarted")
    public String answerQuestion(@ShellOption({"optionNumber"}) String optionNumber) {
        try {
            testingService.saveAnswer(optionNumber, testedStudent);
            return messageLocalizationService.getLocalizedMessage("answer-saved", new Object[]{optionNumber});
        } catch (TestingFinishedException e) {
            return messageLocalizationService.getLocalizedMessage("testing-finished", new Object[]{"get-result"});
        }
    }

    @ShellMethod(key = "get-result", value = "Get test result")
    @ShellMethodAvailability(value = "isTestingStarted")
    public String getResult() {
        TestResult testResult = testingService.getTestResult(testedStudent);

        return messageLocalizationService.getLocalizedMessage("announce-score", new Object[]{testedStudent.getFirstName(), testedStudent.getLastName(), testResult.getCorrectAnswersCount(), testResult.getAnswersCount()});
    }

    private Availability isTestingStarted() {
        return testedStudent != null
                ? Availability.available()
                : Availability.unavailable(messageLocalizationService.getLocalizedMessage("testing-not-started", new Object[]{"start-testing"}));
    }
}
