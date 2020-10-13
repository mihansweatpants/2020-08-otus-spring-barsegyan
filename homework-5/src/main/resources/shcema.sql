create table genre
(
    genre_id bigint primary key,
    name     varchar(255)
);

create sequence seq_genre;

create table author
(
    author_id bigint primary key,
    name      varchar(255)
);

create sequence seq_author;

create table book
(
    book_id   bigint primary key,
    title     varchar(255),
    author_id bigint references author,
    genre_id  bigint references genre
);

create sequence seq_book;
