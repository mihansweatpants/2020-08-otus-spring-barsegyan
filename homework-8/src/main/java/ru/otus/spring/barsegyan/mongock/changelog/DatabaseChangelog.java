package ru.otus.spring.barsegyan.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.barsegyan.domain.Author;
import ru.otus.spring.barsegyan.domain.Book;
import ru.otus.spring.barsegyan.domain.Genre;
import ru.otus.spring.barsegyan.repository.AuthorRepository;
import ru.otus.spring.barsegyan.repository.BookRepository;
import ru.otus.spring.barsegyan.repository.GenreRepository;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "mihansweatpants")
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "init", author = "mihansweatpants")
    public void init(GenreRepository genreRepository,
                     AuthorRepository authorRepository,
                     BookRepository bookRepository) {
        var classic = new Genre("Classic");
        var crimeDetective = new Genre("Crime/Detective");
        var fantasy = new Genre("Fantasy");
        genreRepository.saveAll(List.of(classic, crimeDetective, fantasy));

        var dostoevsky = new Author("Dostoevsky");
        authorRepository.save(dostoevsky);

        var crimeAndPunishment = new Book("Crime and Punishment");
        crimeAndPunishment.setAuthor(dostoevsky);
        crimeAndPunishment.setGenres(List.of(classic, crimeDetective));
        bookRepository.save(crimeAndPunishment);
    }
}
