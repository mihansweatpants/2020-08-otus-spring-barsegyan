package ru.otus.spring.barsegyan.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.otus.spring.barsegyan.domain.Author;
import ru.otus.spring.barsegyan.domain.Book;
import ru.otus.spring.barsegyan.domain.Genre;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("all")
@Configuration
public class JobConfig {
    private static final int CHUNK_SIZE = 5;
    private final Logger logger = LoggerFactory.getLogger("Batch");

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private DataSource dataSource;

    @Bean
    public Job migrateBookDataJob(Step setupStep,
                                  Step loadAuthorsStep,
                                  Step loadGenresStep,
                                  Step loadBooksStep,
                                  Step loadBooksGenresRelationStep,
                                  Step cleanupStep) {
        return jobBuilderFactory.get("migrateBookData")
                .incrementer(new RunIdIncrementer())
                .start(setupStep)
                .next(loadAuthorsStep)
                .next(loadGenresStep)
                .next(loadBooksStep)
                .next(loadBooksGenresRelationStep)
                .next(cleanupStep)
                .build();
    }

    /* Setup */
    @Bean
    public Step setupStep(Tasklet createTemporaryIdMappingTables) {
        return stepBuilderFactory.get("setupStep")
                .tasklet(createTemporaryIdMappingTables)
                .build();
    }

    @Bean
    public Tasklet createTemporaryIdMappingTables() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        return (StepContribution contribution, ChunkContext chunkContext) -> {
            jdbcTemplate.update("create table temp_author_id_mapping(author_mongo_id text, author_id bigserial)");
            jdbcTemplate.update("create table temp_genre_id_mapping(genre_mongo_id text, genre_id bigserial)");
            jdbcTemplate.update("create table temp_book_id_mapping(book_mongo_id text, book_id bigserial)");

            return null;
        };
    }


    /* Load Genres */
    @Bean
    public Step loadGenresStep(ItemReader<Genre> genreItemReader, ItemWriter<Genre> genreItemWriter) {
        return stepBuilderFactory.get("loadGenresStep")
                .<Genre, Genre>chunk(CHUNK_SIZE)
                .reader(genreItemReader)
                .writer(genreItemWriter)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    public ItemReader<Genre> genreItemReader() {
        return createMongoItemReader(Genre.class, "genreItemReader");
    }

    @Bean
    public ItemWriter<Genre> genreItemWriter() {
        return new JdbcBatchItemWriterBuilder<Genre>()
                .dataSource(dataSource)
                .sql("insert into temp_genre_id_mapping (genre_mongo_id) values (:id);" +
                        "insert into genre (name, genre_id) values (:name, select genre_id from temp_genre_id_mapping where genre_mongo_id = :id)")
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .build();
    }

    /* Load authors */
    @Bean
    public Step loadAuthorsStep(ItemReader<Author> authorItemReader, ItemWriter<Author> authorItemWriter) {
        return stepBuilderFactory.get("loadAuthorsStep")
                .<Author, Author>chunk(CHUNK_SIZE)
                .reader(authorItemReader)
                .writer(authorItemWriter)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    public ItemReader<Author> authorItemReader() {
        return createMongoItemReader(Author.class, "authorItemReader");
    }

    @Bean
    public ItemWriter<Author> authorItemWriter() {
        return new JdbcBatchItemWriterBuilder<Author>()
                .dataSource(dataSource)
                .sql("insert into temp_author_id_mapping (author_mongo_id) values (:id);" +
                        "insert into author (name, author_id) values (:name, select author_id from temp_author_id_mapping where author_mongo_id = :id)")
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .build();
    }

    /* Load Books */
    @Bean
    public Step loadBooksStep(ItemReader<Book> bookItemReader, ItemWriter<Book> bookItemWriter) {
        return stepBuilderFactory.get("loadBooksStep")
                .<Book, Book>chunk(CHUNK_SIZE)
                .reader(bookItemReader)
                .writer(bookItemWriter)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    public ItemReader<Book> bookItemReader() {
        return createMongoItemReader(Book.class, "bookItemReader");
    }

    @Bean
    public ItemWriter<Book> bookItemWriter() {
        return new JdbcBatchItemWriterBuilder<Book>()
                .dataSource(dataSource)
                .sql("insert into temp_book_id_mapping (book_mongo_id) values (:id);" +
                        "insert into book (title, author_id, book_id) " +
                        "values (:title, select author_id from temp_author_id_mapping where author_mongo_id = :author.id, select book_id from temp_book_id_mapping where book_mongo_id = :id)")
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .build();
    }

    /* Load books-genres relation */
    @Bean
    public Step loadBooksGenresRelationStep(ItemReader<Book> bookGenreItemReader, ItemWriter<Book> bookGenreItemWriter) {
        return stepBuilderFactory.get("loadBooksGenresRelationStep")
                .<Book, Book>chunk(CHUNK_SIZE)
                .reader(bookGenreItemReader)
                .writer(bookGenreItemWriter)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    public MongoItemReader<Book> bookGenreItemReader() {
        return createMongoItemReader(Book.class, "bookGenreItemReader");
    }

    @Bean
    public ItemWriter<Book> bookGenreItemWriter() {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        return (books) -> {
            for (var book : books) {
                for (var genre : book.getGenres()) {
                    var params = Map.of("book_mongo_id", book.getId(), "genre_mongo_id", genre.getId());

                    jdbcTemplate.update("insert into book_genre (book_id, genre_id) " +
                            "values (select book_id from temp_book_id_mapping where book_mongo_id = :book_mongo_id, " +
                            "select genre_id from temp_genre_id_mapping where genre_mongo_id = :genre_mongo_id)", params);
                }
            }
        };
    }

    /* Cleanup */
    @Bean
    public Step cleanupStep(Tasklet dropTemporaryIdMappingTables) {
        return stepBuilderFactory.get("cleanupStep")
                .tasklet(dropTemporaryIdMappingTables)
                .build();
    }

    @Bean
    public Tasklet dropTemporaryIdMappingTables() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        return (StepContribution contribution, ChunkContext chunkContext) -> {
            jdbcTemplate.update("drop table temp_author_id_mapping");
            jdbcTemplate.update("drop table temp_genre_id_mapping");
            jdbcTemplate.update("drop table temp_book_id_mapping");

            return null;
        };
    }


    private <T> MongoItemReader<T> createMongoItemReader(Class<T> targetType, String name) {
        return new MongoItemReaderBuilder<T>()
                .name("personMongoItemReader")
                .template(mongoTemplate)
                .jsonQuery("{}")
                .targetType(targetType)
                .sorts(new HashMap<>())
                .build();
    }
}

