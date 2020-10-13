package ru.otus.spring.barsegyan.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.barsegyan.dao.AuthorDao;
import ru.otus.spring.barsegyan.dao.BookDao;
import ru.otus.spring.barsegyan.dao.GenreDao;
import ru.otus.spring.barsegyan.domain.Author;
import ru.otus.spring.barsegyan.domain.Book;
import ru.otus.spring.barsegyan.domain.Genre;
import ru.otus.spring.barsegyan.dto.CreateBookDto;
import ru.otus.spring.barsegyan.dto.UpdateBookDto;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final GenreDao genreDao;
    private final AuthorDao authorDao;

    public BookServiceImpl(BookDao bookDao,
                           GenreDao genreDao,
                           AuthorDao authorDao) {
        this.bookDao = bookDao;
        this.genreDao = genreDao;
        this.authorDao = authorDao;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.findAll();
    }

    @Override
    public void createBook(CreateBookDto createBookDto) {
        Genre genre = genreDao.findById(createBookDto.getGenreId());
        Author author = authorDao.findById(createBookDto.getAuthorId());

        Book book = new Book();
        book.setGenre(genre);
        book.setAuthor(author);

        bookDao.create(book);
    }

    @Override
    public void updateBook(UpdateBookDto updateBookDto) {
        Genre genre = genreDao.findById(updateBookDto.getGenreId());
        Author author = authorDao.findById(updateBookDto.getAuthorId());

        bookDao.update(new Book(updateBookDto.getId(), updateBookDto.getTitle(), genre, author));
    }

    @Override
    public void deleteBookById(long id) {
        bookDao.deleteById(id);
    }
}
