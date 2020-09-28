package ru.otus.spring.barsegyan.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.barsegyan.domain.Question;
import ru.otus.spring.barsegyan.domain.Student;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestingServiceImpl implements TestingService {

    private final QuestionService questionService;
    private final IOService ioService;
    private final MessageService messageService;

    private Student testedStudent;
    private List<Question> questions;
    private final List<String> answers = new ArrayList<>();

    public TestingServiceImpl(QuestionService questionService,
                              IOService ioService,
                              MessageService messageService) {
        this.questionService = questionService;
        this.ioService = ioService;
        this.messageService = messageService;
    }

    public void runTesting() {
        createStudent();
        loadQuestions();

        for (Question question : questions) {
            askQuestion(question);
        }

        announceScore();
    }

    private void createStudent() {
        ioService.write(messageService.getLocalizedMessage("first-name"));
        String firstName = ioService.read();
        ioService.write(messageService.getLocalizedMessage("last-name"));
        String lastName = ioService.read();

        testedStudent = new Student(firstName, lastName);
    }

    private void loadQuestions() {
        questions = questionService.getQuestions();
    }

    private void askQuestion(Question question) {
        ioService.write(question.getQuestionText());
        question.getAnswerOptions().forEach(ioService::write);

        ioService.write(messageService.getLocalizedMessage("answer-to-question"));
        String answer = ioService.read();
        answers.add(answer);
    }

    private int getScore() {
        int correctAnswers = 0;

        for (int i = 0; i < answers.size(); i++) {
            var answer = answers.get(i);
            var correctAnswer = questions.get(i).getCorrectAnswerNumber();
            if (answer.equals(correctAnswer)) correctAnswers++;
        }

        return correctAnswers;
    }

    private void announceScore() {
        ioService.write(messageService.getLocalizedMessage("announce-score", new Object[]{testedStudent.getFirstName(), testedStudent.getLastName(), getScore(), questions.size()}));
    }
}
