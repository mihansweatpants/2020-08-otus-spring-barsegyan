package ru.otus.spring.barsegyan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private long id;

    @Column(name = "title")
    private String title;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "book_genre", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "book_id")
    private List<BookReview> reviews = new ArrayList<>();
}
