package ru.otus.spring.barsegyan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.barsegyan.model.BookDiscount;

@Repository
public interface BookDiscountRepository extends JpaRepository<BookDiscount, Long> {
}
