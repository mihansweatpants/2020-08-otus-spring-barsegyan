package ru.otus.spring.barsegyan.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.barsegyan.dao.mappers.AuthorMapper;
import ru.otus.spring.barsegyan.domain.Author;

import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations jdbc;

    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public int count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from author", Integer.class);
    }

    @Override
    public List<Author> findAll() {
        return jdbc.query("select author_id, name as author_name from author", new AuthorMapper());
    }

    @Override
    public Author findById(long id) {
        return jdbc.queryForObject("select author_id, name as author_name from author where author_id = :author_id",
                Map.of("author_id", id),
                new AuthorMapper());
    }

    @Override
    public void create(Author author) {
        jdbc.update("insert into author (author_id, name) values (:author_id, :name)",
                Map.of("author_id", author.getId(), "name", author.getName()));
    }
}
