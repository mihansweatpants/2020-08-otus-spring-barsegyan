# Домашнее задание 18

### Описание задания:

Обернуть внешние вызовы в Hystrix

Цель: сделать внешние вызовы приложения устойчивыми к ошибкам

Результат: приложение с изолированными с помощью Hystrix внешними вызовами

1. Обернуть все внешние вызовы в Hystrix, Hystrix Javanica.
2. Возможно использование Resilent4j
3. Возможно использование Feign Client


### Описание решения

В проекте 2 сервиса

1) book-store-service http://localhost:8080/swagger-ui.html
2) book-discount-service http://localhost:8081/swagger-ui.html

book-store-service вызывает book-discount-service (GET /discounts/{bookId}), чтобы получить данные о скидке для книги. Данный вызов обернут в Hystrix и возвращает фоллбэк, если book-discount-service недоступен или время ожидания ответа превышает таймаут.
Для наглядности работы Hystrix в book-discount-service симулируется случайная задержка (от 0.5 до 1.5 секунды).
