package ru.otus.spring.barsegyan.service;

import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.Scanner;

@Service
public class IOServiceConsole implements IOService {
    private final Scanner scanner;
    private final PrintStream printStream;

    public IOServiceConsole() {
        this.scanner = new Scanner(System.in);
        this.printStream = System.out;
    }

    public String read() {
        return scanner.nextLine();
    }

    public void write(String output) {
        printStream.println(output);
    }
}
