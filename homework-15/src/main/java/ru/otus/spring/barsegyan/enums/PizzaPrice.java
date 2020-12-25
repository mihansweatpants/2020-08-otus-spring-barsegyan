package ru.otus.spring.barsegyan.enums;

import java.util.Map;

import static ru.otus.spring.barsegyan.enums.PizzaSize.L;
import static ru.otus.spring.barsegyan.enums.PizzaSize.M;
import static ru.otus.spring.barsegyan.enums.PizzaSize.S;

public class PizzaPrice {
    private static final Map<PizzaSize, Integer> PRICE = Map.of(S, 500, M, 750, L, 999);

    public static int getPrice(PizzaSize pizzaSize){
        return PRICE.get(pizzaSize);
    }
}
