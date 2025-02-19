package com.example.mealcalendar;

/**
 * Eccezione personalizzata da lanciare quando nessuna ricetta è stata selezionata per la modifica.
 */
public class RecipeNotSelectedException extends Exception {
    public RecipeNotSelectedException() {
        super("Nessuna ricetta selezionata per la modifica.");
    }

    public RecipeNotSelectedException(String message) {
        super(message);
    }
}
