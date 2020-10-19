insert into genre (genre_id, name)
values (1, 'Классическая литература'),
       (2, 'Роман');

insert into author (author_id, name)
values (1, 'Достоевский Ф.М.'),
       (2, 'Пушкин А.С.');

insert into book (book_id, title, author_id)
values (1, 'Преступление и наказание', 1),
       (2, 'Дубровский', 2);

insert into book_genre (book_id, genre_id)
values (1, 1),
       (1, 2),
       (2, 1),
       (2, 2);

insert into book_review (book_review_id, book_id, review_text)
values (1, 1, 'Хорошая книга!'),
       (2, 1, 'Скукотища'),
       (3, 2, 'бла бла бла');
