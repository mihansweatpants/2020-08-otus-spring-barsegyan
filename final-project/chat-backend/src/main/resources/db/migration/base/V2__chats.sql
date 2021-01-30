create table chat
(
    chat_id          uuid not null primary key,
    name             text,
    last_update_time timestamp
);

create table app_user_chat
(
    chat_id     uuid references chat,
    app_user_id uuid references app_user
);