package com.example.mealcalendar;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.*;

public class AntiCodeSmellPrinter {

    private static final Logger LOGGER = Logger.getLogger(AntiCodeSmellPrinter.class.getName());

    static {

        LogManager.getLogManager().reset();

        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.INFO);


        handler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord rec) {
                return rec.getMessage() + System.lineSeparator();
            }
        });

        LOGGER.addHandler(handler);
        LOGGER.setLevel(Level.INFO);
        LOGGER.setUseParentHandlers(false);
    }

    private final String name;

    public AntiCodeSmellPrinter(String name) {
        this.name = name;
    }

    public void print(String msg) {
        String formatted = String.format("[%s]: %s", name, msg);
        LOGGER.info(formatted);
    }
}

