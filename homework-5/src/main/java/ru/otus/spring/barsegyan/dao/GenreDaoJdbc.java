package ru.otus.spring.barsegyan.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.barsegyan.dao.mappers.GenreMapper;
import ru.otus.spring.barsegyan.domain.Genre;

import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbc;

    public GenreDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public int count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from genre", Integer.class);
    }

    @Override
    public List<Genre> findAll() {
        return jdbc.query("select genre_id, name as genre_name from genre", new GenreMapper());
    }

    @Override
    public Genre findById(long id) {
        return jdbc.queryForObject("select genre_id, name as genre_name from genre where genre_id = :genre_id",
                Map.of("genre_id", id),
                new GenreMapper());
    }

    @Override
    public void create(Genre genre) {
        jdbc.update("insert into genre (genre_id, name) values (:genre_id, :name)",
                Map.of("genre_id", genre.getId(), "name", genre.getName()));
    }
}
