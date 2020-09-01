package ru.otus.spring.barsegyan;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.barsegyan.service.QuestionService;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");

        QuestionService questionService = context.getBean(QuestionService.class);
        questionService.printQuestions();

        context.close();
    }
}
