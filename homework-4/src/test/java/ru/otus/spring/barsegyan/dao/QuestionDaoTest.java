package ru.otus.spring.barsegyan.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.barsegyan.config.AppProps;
import ru.otus.spring.barsegyan.domain.Question;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Questions service")
public class QuestionDaoTest {
    private final static String correctFileName = "questions_en.csv";
    private final static String wrongFileName = "abracadabra.csv";

    private AppProps appProps;

    @BeforeEach
    void setUp() {
        appProps = new AppProps();
        appProps.setLocale(Locale.ENGLISH);
    }

    @DisplayName("loads questions from file")
    @Test
    void loadsQuestionsIfResourceExists() {
        appProps.setQuestionsFileNameEn(correctFileName);

        QuestionDao questionDao = new QuestionDaoCsv(appProps);

        List<Question> questions = questionDao.findAll();

        assertEquals(5, questions.size());
    }

    @DisplayName("loads questions from file")
    @Test
    void returnsEmptyQuestionsIfResourceDoesntExist() {
        appProps.setQuestionsFileNameEn(wrongFileName);

        QuestionDao questionDao = new QuestionDaoCsv(appProps);

        List<Question> questions = questionDao.findAll();

        assertEquals(0, questions.size());
    }
}
