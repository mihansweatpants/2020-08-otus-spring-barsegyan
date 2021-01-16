package ru.otus.spring.barsegyan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDiscountDto {
    private Long bookId;
    private Integer discount;
}
