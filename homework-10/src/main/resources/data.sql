insert into genre (genre_id, name)
values (1, 'Классическая литература'),
       (2, 'Роман'),
       (3, 'Научная фантастика'),
       (4, 'Фантастика'),
       (5, 'Детектив');

insert into author (author_id, name)
values (1, 'Достоевский Ф.М.'),
       (2, 'Пушкин А.С.'),
       (3, 'Агата Кристи'),
       (4, 'Станислав Лем');

insert into book (book_id, title, author_id)
values (1, 'Преступление и наказание', 1),
       (2, 'Дубровский', 2),
       (3, 'Убийство в «Восточном экспрессе»', 3),
       (4, 'Загадка Эндхауза', 3),
       (5, 'Солярис', 4),
       (6, 'Глас Господа', 4),
       (7, 'Идиот', 1);

insert into book_genre (book_id, genre_id)
values (1, 1),
       (1, 2),
       (2, 1),
       (2, 2),
       (6, 2),
       (6, 3),
       (5, 2),
       (5, 3),
       (3, 5),
       (4, 5),
       (7, 1),
       (7, 2);

insert into book_review (book_review_id, book_id, review_text, created_at)
values (1, 1, 'Хорошая книга!', current_timestamp),
       (2, 1, 'Скукотища', current_timestamp),
       (3, 2, 'бла бла бла', current_timestamp);
