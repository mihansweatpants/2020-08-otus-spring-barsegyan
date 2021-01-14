package ru.otus.spring.barsegyan.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "book_discount")
@Data
@NoArgsConstructor
public class BookDiscount {
    @Id
    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "discount")
    private Integer discount;
}
