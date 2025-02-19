package com.example.mealcalendar;

public class RecipeEditException extends Exception {
    public RecipeEditException(String message) {
        super(message);
    }

    public RecipeEditException(String message, Throwable cause) {
        super(message, cause);
    }
}
