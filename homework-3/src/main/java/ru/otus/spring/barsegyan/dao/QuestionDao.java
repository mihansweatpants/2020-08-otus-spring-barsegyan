package ru.otus.spring.barsegyan.dao;

import ru.otus.spring.barsegyan.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> findAll();
}
