package ru.otus.spring.barsegyan.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.barsegyan.dto.BookDiscountDto;
import ru.otus.spring.barsegyan.exception.NotFoundException;
import ru.otus.spring.barsegyan.model.BookDiscount;
import ru.otus.spring.barsegyan.repository.BookDiscountRepository;

import java.util.Random;

@Api
@RestController
public class BookDiscountController {
    private final static Logger logger = LoggerFactory.getLogger(BookDiscountController.class);

    private final BookDiscountRepository bookDiscountRepository;

    public BookDiscountController(BookDiscountRepository bookDiscountRepository) {
        this.bookDiscountRepository = bookDiscountRepository;
    }

    @GetMapping("/discounts/{bookId}")
    public BookDiscountDto getDiscountByBookId(@PathVariable Long bookId) {
        simulateLatency();

        BookDiscount bookDiscount = bookDiscountRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException(String.format("Discount for book %s not found", bookId)));

        return new BookDiscountDto(bookDiscount.getBookId(), bookDiscount.getDiscount());
    }

    private void simulateLatency() {
        long millis = 500 + 100 * new Random().nextInt(10);

        logger.info("Simulating network latency. Sleeping for {}ms", millis);

        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
