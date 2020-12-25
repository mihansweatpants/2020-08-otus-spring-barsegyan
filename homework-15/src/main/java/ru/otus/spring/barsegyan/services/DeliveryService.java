package ru.otus.spring.barsegyan.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.spring.barsegyan.model.ReadyOrder;

@Service
public class DeliveryService {
    private static final Logger LOG = LoggerFactory.getLogger("delivery");

    public ReadyOrder way(ReadyOrder order) {
        LOG.info("\nВ пути\n{}", order);
        return order;
    }
}
