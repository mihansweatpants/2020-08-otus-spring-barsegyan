package ru.otus.spring.barsegyan.dto;

import lombok.Data;

@Data
public class BookDiscountDto {
    private final Long bookId;
    private final Integer discount;
}
