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

insert into book_review (book_review_id, book_id, review_text, created_at)
values (1, 1, 'Хорошая книга!', current_timestamp),
       (2, 1, 'Скукотища', current_timestamp),
       (3, 2, 'бла бла бла', current_timestamp);

insert into app_user (app_user_id, username, password)
values (1, 'admin', '$2a$10$jvNMCO4mLpXlxBfGS9VwWeTuyuV66DlW9o4zKs7FYZB8mfbAqDUCS'); -- bcrypt(admin)
