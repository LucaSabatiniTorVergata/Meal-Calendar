package com.example.mealcalendar;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class AntiCodeSmellPrinter {

    private static final PrintWriter OUT = new PrintWriter(new OutputStreamWriter(System.out), true);

    private final String name;

    public AntiCodeSmellPrinter(String name) {
        this.name = name;
    }

    public void print(String msg) {
        OUT.println("[" + name + "]: " + msg);
    }
}