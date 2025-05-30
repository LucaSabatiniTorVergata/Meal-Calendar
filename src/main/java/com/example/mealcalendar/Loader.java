package com.example.mealcalendar;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Loader {
    private static final Properties queries = new Properties();

    static {
        try (InputStream input = Loader.class.getClassLoader().getResourceAsStream("Query/queries.properties")) {
            if (input == null) {
                throw new IOException("File delle query non trovato!");
            }
            queries.load(input);
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Errore nel caricamento delle query: " + e.getMessage());
        }
    }

    public static String getQuery(String key) {
        return queries.getProperty(key);
    }
}