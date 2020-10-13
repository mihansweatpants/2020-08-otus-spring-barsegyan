package ru.otus.spring.barsegyan.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.spring.barsegyan.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper implements RowMapper<Author> {
    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Author(rs.getLong("author_id"), rs.getString("author_name"));
    }
}
