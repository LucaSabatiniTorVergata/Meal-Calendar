package com.example.mealcalendar;

import java.io.PrintStream;

public class AntiCodeSmellPrinter {

    private static final PrintStream OUT = System.out; // Usa direttamente System.out

    private final String name;

    public AntiCodeSmellPrinter(String name) {
        this.name = name;
    }

    public void print(String msg) {
        synchronized (OUT) {
            OUT.println(String.format("[%s]: %s", name, msg));
        }
    }
}