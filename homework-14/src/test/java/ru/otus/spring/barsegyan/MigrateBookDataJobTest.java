package ru.otus.spring.barsegyan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.otus.spring.barsegyan.domain.Book;
import ru.otus.spring.barsegyan.repository.AuthorRepository;
import ru.otus.spring.barsegyan.repository.BookRepository;
import ru.otus.spring.barsegyan.repository.GenreRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@SpringBatchTest
public class MigrateBookDataJobTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private BookRepository bookRepository;


    @BeforeEach
    void clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void allJobStepsComplete() {
        assertThat(jobLauncherTestUtils.launchStep("setupStep").getStatus()).isEqualTo(BatchStatus.COMPLETED);
        assertThat(jobLauncherTestUtils.launchStep("loadAuthorsStep").getStatus()).isEqualTo(BatchStatus.COMPLETED);
        assertThat(jobLauncherTestUtils.launchStep("loadGenresStep").getStatus()).isEqualTo(BatchStatus.COMPLETED);
        assertThat(jobLauncherTestUtils.launchStep("loadBooksStep").getStatus()).isEqualTo(BatchStatus.COMPLETED);
        assertThat(jobLauncherTestUtils.launchStep("loadBooksGenresRelationStep").getStatus()).isEqualTo(BatchStatus.COMPLETED);
        assertThat(jobLauncherTestUtils.launchStep("cleanupStep").getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }

    @Test
    void allDataIsCopied() throws Exception {
        jobLauncherTestUtils.launchJob();

        var expectedNumberOfAuthors = jdbcTemplate.queryForObject("select count(*) from author", Integer.class);
        var actualNumberOfAuthors = authorRepository.count();
        assertThat(expectedNumberOfAuthors).isEqualTo(actualNumberOfAuthors);

        var expectedNumberOfGenres = jdbcTemplate.queryForObject("select count(*) from genre", Integer.class);
        var actualNumberOfGenres = genreRepository.count();
        assertThat(expectedNumberOfGenres).isEqualTo(actualNumberOfGenres);

        var expectedNumberOfBooks = jdbcTemplate.queryForObject("select count(*) from book", Integer.class);
        var actualNumberOfBooks = bookRepository.count();
        assertThat(expectedNumberOfBooks).isEqualTo(actualNumberOfBooks);

        var expectedNumberOfBookGenreRelations = jdbcTemplate.queryForObject("select count(*) from book_genre", Integer.class);
        var actualNumberOfBookGenreRelations = bookRepository.findAll().stream().map(Book::getGenres).map(List::size).reduce(0, Integer::sum);
        assertThat(expectedNumberOfBookGenreRelations).isEqualTo(actualNumberOfBookGenreRelations);
    }

    @Test
    void temporaryTablesAreNotInDB() throws Exception {
        jobLauncherTestUtils.launchJob();

        assertThrows(Exception.class, () -> jdbcTemplate.execute("select from temp_author_id_mapping"));
        assertThrows(Exception.class, () -> jdbcTemplate.execute("select from temp_genre_id_mapping"));
        assertThrows(Exception.class, () -> jdbcTemplate.execute("select from temp_book_id_mapping"));
    }

    @Test
    void dataIsCopiedCorrectly() throws Exception {
        jobLauncherTestUtils.launchJob();

        List<Book> sourceBookData = bookRepository.findAll(Sort.by(Sort.Direction.ASC, "title"));
        List<Map<String, Object>> copiedBookData = jdbcTemplate.queryForList(
                "select book.title as book_title, author.name as author_name\n" +
                        "from book\n" +
                        "         join author on book.author_id = author.author_id\n" +
                        "order by title asc;"
        );

        assertThat(sourceBookData.size()).isEqualTo(copiedBookData.size());

        IntStream.range(0, sourceBookData.size()).forEach(index -> {
            var sourceBook = sourceBookData.get(index);
            var copiedBook = copiedBookData.get(index);

            assertThat(sourceBook.getTitle()).isEqualTo(copiedBook.get("BOOK_TITLE"));
            assertThat(sourceBook.getAuthor().getName()).isEqualTo(copiedBook.get("AUTHOR_NAME"));
        });

        var copiedBookGenreRelations = jdbcTemplate.queryForList(
                "select genre.name as genre_name, book.title as book_title\n" +
                        "from genre\n" +
                        "         join book_genre on genre.genre_id = book_genre.genre_id\n" +
                        "         join book on book_genre.book_id = book.book_id;"
        );

        copiedBookGenreRelations.forEach(bookGenreRelation -> {
            var sourceBook = sourceBookData
                    .stream()
                    .filter(book -> book.getTitle().equals(bookGenreRelation.get("BOOK_TITLE")))
                    .findFirst();

            assertThat(sourceBook)
                    .isNotEmpty()
                    .get()
                    .matches(book -> book.getGenres()
                            .stream()
                            .anyMatch(genre -> genre.getName().equals(bookGenreRelation.get("GENRE_NAME"))));
        });
    }
}
