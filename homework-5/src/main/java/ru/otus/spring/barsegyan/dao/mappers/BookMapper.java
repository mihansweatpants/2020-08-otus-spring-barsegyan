package ru.otus.spring.barsegyan.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.spring.barsegyan.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        GenreMapper genreMapper = new GenreMapper();
        AuthorMapper authorMapper = new AuthorMapper();

        return new Book(
                rs.getLong("book_id"),
                rs.getString("title"),
                genreMapper.mapRow(rs, rowNum),
                authorMapper.mapRow(rs, rowNum));
    }
}

