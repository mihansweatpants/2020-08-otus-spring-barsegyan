package ru.otus.spring.barsegyan.exception;

public class PizzaMakeException extends RuntimeException{
    public PizzaMakeException(String message) {
        super(message);
    }
}
