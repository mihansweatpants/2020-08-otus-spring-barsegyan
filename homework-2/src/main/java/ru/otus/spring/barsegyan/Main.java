package ru.otus.spring.barsegyan;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.barsegyan.service.TestingService;

@PropertySource("classpath:application.properties")
@ComponentScan
@Configuration
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        TestingService testingService = context.getBean(TestingService.class);
        testingService.runTesting();

        context.close();
    }
}
