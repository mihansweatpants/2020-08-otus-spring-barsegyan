insert into genre (genre_id, name)
values (1, 'Test genre');
insert into genre (genre_id, name)
values (2, 'Another genre');

insert into author (author_id, name)
values (1, 'Test author');
insert into author (author_id, name)
values (2, 'Another author');

insert into book (book_id, title, genre_id, author_id)
values (1, 'Test book', 1, 1);
insert into book (book_id, title, genre_id, author_id)
values (3, 'Delete me', 2, 1);
