package ru.otus.spring.barsegyan.dao;

import com.opencsv.CSVReader;
import org.springframework.stereotype.Component;
import ru.otus.spring.barsegyan.config.AppProps;
import ru.otus.spring.barsegyan.domain.Question;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Component
public class QuestionDaoCsv implements QuestionDao {

    private String csvFileName;
    private final List<Question> questions = new ArrayList<>();

    public QuestionDaoCsv(AppProps appProps) {
        this.csvFileName = appProps.getLocale().equals(Locale.ENGLISH) ? appProps.getQuestionsFileNameEn() : appProps.getQuestionsFileNameRu();
    }

    public List<Question> findAll() {
        if (questions.isEmpty()) {
            loadQuestions();
        }

        return questions;
    }

    public Question findByNumber(int questionNumber) {
        return findAll().get(questionNumber);
    }

    public int questionsTotal() {
        return findAll().size();
    }

    private void loadQuestions() {
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(csvFileName);

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream))) {
            List<String[]> parsedCsvLines = csvReader.readAll();

            parsedCsvLines.forEach(line -> {
                String questionText = line[0];
                String correctAnswer = line[1];
                List<String> answerOptions = Arrays.asList(Arrays.copyOfRange(line, 2, line.length));

                questions.add(new Question(questionText, correctAnswer, answerOptions));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
