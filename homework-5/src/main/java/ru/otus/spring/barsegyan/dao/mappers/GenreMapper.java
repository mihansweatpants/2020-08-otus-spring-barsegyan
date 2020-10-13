package ru.otus.spring.barsegyan.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.spring.barsegyan.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class GenreMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(rs.getLong("genre_id"), rs.getString("genre_name"));
    }
}
