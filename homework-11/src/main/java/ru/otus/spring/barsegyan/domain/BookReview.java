package ru.otus.spring.barsegyan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "book_reviews")
public class BookReview {
    @Id
    private String id;

    @DBRef
    private Book book;

    private String text;

    private LocalDateTime createdAt;

    public BookReview setBook(Book book) {
        this.book = book;
        return this;
    }

    public BookReview setText(String text) {
        this.text = text;
        return this;
    }

    public BookReview setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
