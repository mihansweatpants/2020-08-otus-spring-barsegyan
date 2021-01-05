package ru.otus.spring.barsegyan.actuators;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        LocalDateTime now = LocalDateTime.now();

        return now.getHour() < 8
                ? Health.down().withDetail("message", "Application is sleeping. Come back after 8 in the morning").build()
                : Health.up().withDetail("message", "Application is awake").build();
    }
}
