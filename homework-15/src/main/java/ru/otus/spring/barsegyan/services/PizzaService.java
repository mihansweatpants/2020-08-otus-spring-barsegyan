package ru.otus.spring.barsegyan.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.spring.barsegyan.model.Order;
import ru.otus.spring.barsegyan.model.Pizza;
import ru.otus.spring.barsegyan.model.ReadyOrder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PizzaService {
    private static final Logger LOG = LoggerFactory.getLogger("kitchen");

    public ReadyOrder makePizza(Order order) {
        LOG.info("\nПриготовление {}", order);
        try {
            Thread.sleep(100 * order.getOrderItems().size());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        List<Pizza> pizzas = order.getOrderItems().stream()
                .map(it -> new Pizza(it.getName(), it.getSize()))
                .collect(Collectors.toList());
        return new ReadyOrder(order.getNumber(),
                order.getOrderPrice(),
                order.getPhoneNumber(),
                order.getAddress(),
                pizzas);
    }
}
