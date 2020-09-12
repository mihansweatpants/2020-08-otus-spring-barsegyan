package ru.otus.spring.barsegyan.dao;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.spring.barsegyan.domain.Question;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class QuestionDaoCsv implements QuestionDao {

    private String csvFilePath;
    private final List<Question> questions = new ArrayList<>();

    public QuestionDaoCsv(@Value("${questionsFilePath}") String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    public List<Question> findAll() {
        if (questions.isEmpty()) {
            loadQuestions();
        }

        return questions;
    }

    private void loadQuestions() {
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(csvFilePath);

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
