insert into genre (genre_id, name)
values (1, 'Классическая литература');

insert into author (author_id, name)
values (1, 'Достоевский');

insert into book (book_id, title, author_id, genre_id)
values (1, 'Преступление и наказание', 1, 1),
       (2, 'Братья Карамазовы', 1, 1),
       (3, 'Идиот', 1, 1),
       (4, 'Бесы', 1, 1);