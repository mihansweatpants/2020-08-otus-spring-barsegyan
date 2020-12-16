create table genre
(
    genre_id bigserial primary key,
    name     varchar(255)
);

create table author
(
    author_id bigserial primary key,
    name      varchar(255)
);

create table book
(
    book_id   bigserial primary key,
    title     varchar(255),
    author_id bigint references author
);

create table book_genre
(
    book_id  bigint references book on delete cascade,
    genre_id bigint references genre
);
