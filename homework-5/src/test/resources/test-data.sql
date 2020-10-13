insert into genre (genre_id, name)
values ((select nextval('seq_genre')), 'Test genre'),
       ((select nextval('seq_genre')), 'Another genre');

insert into author (author_id, name)
values ((select nextval('seq_author')), 'Test author'),
       ((select nextval('seq_author')), 'Another author');

insert into book (book_id, title, genre_id, author_id)
values ((select nextval('seq_book')), 'Test book', 1, 1),
       ((select nextval('seq_book')), 'Delete me', 2, 1);
