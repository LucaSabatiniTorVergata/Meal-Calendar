package com.example.mealcalendar;

// Eccezione personalizzata per la gestione degli errori nell'applicazione
public class MealCalendarException extends Exception {

    // Costruttore che accetta un messaggio di errore
    public MealCalendarException(String message) {
        super(message);
    }

    // Costruttore che accetta un messaggio di errore e la causa dell'eccezione
    public MealCalendarException(String message, Throwable cause) {
        super(message, cause);
    }
}
