package ru.otus.spring.barsegyan.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.barsegyan.dao.QuestionDao;
import ru.otus.spring.barsegyan.domain.Question;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao questionDao;

    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public List<Question> getQuestions() {
        return questionDao.findAll();
    }
}
