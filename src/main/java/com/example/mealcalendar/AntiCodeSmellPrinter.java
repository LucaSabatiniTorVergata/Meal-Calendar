package com.example.mealcalendar;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AntiCodeSmellPrinter {

    private static final Logger LOGGER = Logger.getLogger(AntiCodeSmellPrinter.class.getName());

    private final String name;

    public AntiCodeSmellPrinter(String name) {
        this.name = name;
    }

    public void print(String msg) {
        LOGGER.log(Level.INFO, "[{0}]: {1}", new Object[]{name, msg});
    }
}