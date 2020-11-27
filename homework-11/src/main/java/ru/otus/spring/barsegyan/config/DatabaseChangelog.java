package ru.otus.spring.barsegyan.config;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {
    @ChangeSet(order = "001", id = "dropDb", author = "mihansweatpants")
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "init", author = "mihansweatpants")
    public void init(MongoDatabase db) {
        MongoCollection<Document> genresCollection = db.getCollection("genres");
        var classic = new Document().append("name", "Classic");
        var crime = new Document().append("name", "Crime");
        var detective = new Document().append("name", "Detective");
        var fantasy = new Document().append("name", "Fantasy");
        genresCollection.insertMany(List.of(classic, crime, detective, fantasy));

        MongoCollection<Document> authorsCollection = db.getCollection("authors");
        var dostoevsky = new Document().append("name", "Dostoevsky");
        authorsCollection.insertOne(dostoevsky);

        MongoCollection<Document> booksCollection = db.getCollection("books");
        var crimeAndPunishment = new Document()
                .append("title", "Crime and Punishment")
                .append("author", dostoevsky)
                .append("genres", List.of(classic, crime, detective));
        booksCollection.insertOne(crimeAndPunishment);
    }
}
