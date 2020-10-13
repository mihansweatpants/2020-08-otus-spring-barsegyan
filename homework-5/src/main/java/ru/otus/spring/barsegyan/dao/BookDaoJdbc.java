package ru.otus.spring.barsegyan.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.barsegyan.dao.mappers.BookMapper;
import ru.otus.spring.barsegyan.domain.Book;

import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations jdbc;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public int count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from book", Integer.class);
    }

    @Override
    public List<Book> findAll() {
        return jdbc.query("select book_id, title, genre.genre_id, genre.name as genre_name, author.name as author_name, author.author_id " +
                "from book " +
                "inner join genre on genre.genre_id = book.genre_id " +
                "inner join author on author.author_id = book.author_id", new BookMapper());
    }

    @Override
    public Book findById(long id) {
        return jdbc.queryForObject("select book_id, title, genre.genre_id, genre.name as genre_name, author.name as author_name, author.author_id " +
                "from book " +
                "inner join genre on genre.genre_id = book.genre_id " +
                "inner join author on author.author_id = book.author_id " +
                "where book_id = :id", Map.of("id", id), new BookMapper());
    }

    @Override
    public void create(Book book) {
        jdbc.update("insert into book (book_id, title, genre_id, author_id) " +
                        "values ((select seq_book.nextval), :title, :genre_id, :author_id)",
                Map.of(
                        "title", book.getTitle(),
                        "genre_id", book.getGenre().getId(),
                        "author_id", book.getAuthor().getId()
                ));
    }

    @Override
    public void update(Book book) {
        jdbc.update("update book set title = :title, genre_id = :genre_id, author_id = :author_id where book_id = :id",
                Map.of(
                        "id", book.getId(),
                        "title", book.getTitle(),
                        "genre_id", book.getGenre().getId(),
                        "author_id", book.getAuthor().getId()
                ));
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from book where book_id = :id", Map.of("id", id));
    }
}
