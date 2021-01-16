package ru.otus.spring.barsegyan.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.otus.spring.barsegyan.dto.BookDiscountDto;

import java.util.Map;

@Service
public class BookDiscountService {
    private final String discountServiceUrl;
    private final RestTemplate restTemplate;

    public BookDiscountService(@Value("${discount-service.url}") String discountServiceUrl) {
        this.discountServiceUrl = discountServiceUrl;
        this.restTemplate = new RestTemplate();
    }

    @HystrixCommand(
            commandProperties = {
                    @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "1000"),
            },
            fallbackMethod = "getDiscountByBookIdFallback"
    )
    public BookDiscountDto getDiscountByBookId(long bookId) {
        ResponseEntity<BookDiscountDto> responseEntity =
                restTemplate.getForEntity(discountServiceUrl + "/discounts/{bookId}", BookDiscountDto.class, Map.of("bookId", bookId));

        return responseEntity.getBody();
    }

    public BookDiscountDto getDiscountByBookIdFallback(long bookId) {
        return new BookDiscountDto(bookId, 0);
    }
}
