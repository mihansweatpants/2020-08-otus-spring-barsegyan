insert into genre (genre_id, name)
values ((select nextval('seq_genre')), 'Классическая литература');

insert into author (author_id, name)
values ((select nextval('seq_author')), 'Достоевский');

insert into book (book_id, title, author_id, genre_id)
values ((select nextval('seq_book')), 'Преступление и наказание', 1, 1),
       ((select nextval('seq_book')), 'Братья Карамазовы', 1, 1),
       ((select nextval('seq_book')), 'Идиот', 1, 1),
       ((select nextval('seq_book')), 'Бесы', 1, 1);
