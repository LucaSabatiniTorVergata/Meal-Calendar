package com.example.mealcalendar;

import java.io.BufferedWriter;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class AntiCodeSmellPrinter {

    private static final PrintWriter OUT = new PrintWriter(new BufferedWriter(
            new OutputStreamWriter(new FileOutputStream(FileDescriptor.out))), true);

    private final String name;

    public AntiCodeSmellPrinter(String name) {
        this.name = name;
    }

    public void print(String msg) {
        OUT.println("[" + name + "]: " + msg);
    }
}