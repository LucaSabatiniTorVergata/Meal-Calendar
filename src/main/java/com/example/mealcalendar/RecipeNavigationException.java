package com.example.mealcalendar;

public class RecipeNavigationException extends Exception {
    public RecipeNavigationException(String message) {
        super(message);
    }

    public RecipeNavigationException(String message, Throwable cause) {
        super(message, cause);
    }
}
