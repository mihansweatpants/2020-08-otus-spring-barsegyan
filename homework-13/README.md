# Домашнее задание 13

### Описание задания:

Ввести авторизацию на основе URL и/или доменных сущностей

Цель: научиться защищать приложение с помощью полноценной авторизации и разграничением прав доступа

Результат: полноценное приложение с безопасностью на основе Spring Security

### Описание решения

В приложении две роли:
1) ROLE_ADMIN (в базе есть пользователь admin/admin)
2) ROLE_USER (в базе есть пользователь test_user/test_user)

Реализована авторизация на основе URL

Пользователи с ролью ROLE_USER могут выполнять CRUD операции с книгами, жанрами, авторами, оставлять рецензии на книги и обновлять данные своих учетных записей (логин/пароль)

Пользователям с ролью ROLE_ADMIN доступны все операции ROLE_USER + просмотр списка пользователей системы, блокирование/разблокирование учетных записей и добавление новых