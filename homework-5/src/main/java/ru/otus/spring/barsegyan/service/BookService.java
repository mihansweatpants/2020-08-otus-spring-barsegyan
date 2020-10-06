package ru.otus.spring.barsegyan.service;

import ru.otus.spring.barsegyan.domain.Book;
import ru.otus.spring.barsegyan.dto.CreateBookDto;
import ru.otus.spring.barsegyan.dto.UpdateBookDto;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();

    void createBook(CreateBookDto createBookDto);

    void updateBook(UpdateBookDto updateBookDto);

    void deleteBookById(long id);
}
