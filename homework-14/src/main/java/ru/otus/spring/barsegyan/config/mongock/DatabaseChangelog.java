package ru.otus.spring.barsegyan.config.mongock;

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
        var classic = new Genre("Классическая литература");
        var detective = new Genre("Детектив");
        var fantasy = new Genre("Фантастика");
        var novel = new Genre("Роман");
        genreRepository.saveAll(List.of(classic, detective, fantasy, novel));

        var dostoevsky = new Author("Достоевский Ф.М.");
        var christie = new Author("Агата Кристи");
        var pushkin = new Author("Пушкин А.С.");
        var lem = new Author("Станислав Лем");
        authorRepository.saveAll(List.of(dostoevsky, christie, pushkin, lem));

        var crimeAndPunishment = new Book("Преступление и наказание");
        crimeAndPunishment.setAuthor(dostoevsky);
        crimeAndPunishment.setGenres(List.of(classic, detective));

        var dubrovsky = new Book("Дубровский");
        dubrovsky.setAuthor(pushkin);
        dubrovsky.setGenres(List.of(classic, novel));

        var murderInEastExp = new Book("Убийство в «Восточном экспрессе»");
        murderInEastExp.setAuthor(christie);
        murderInEastExp.setGenres(List.of(detective));

        var mysteryOfEndh = new Book("Загадка Эндхауза");
        mysteryOfEndh.setAuthor(christie);
        mysteryOfEndh.setGenres(List.of(detective));

        var solaris = new Book("Сорярис");
        solaris.setAuthor(lem);
        solaris.setGenres(List.of(fantasy));

        var hisMastersVoice = new Book("Глас господа");
        hisMastersVoice.setAuthor(lem);
        hisMastersVoice.setGenres(List.of(fantasy, novel));

        var idiot = new Book("Идиот");
        idiot.setAuthor(dostoevsky);
        idiot.setGenres(List.of(classic, novel));

        bookRepository.saveAll(
                List.of(
                        crimeAndPunishment,
                        dubrovsky,
                        murderInEastExp,
                        mysteryOfEndh,
                        solaris,
                        hisMastersVoice,
                        idiot
                )
        );
    }
}
