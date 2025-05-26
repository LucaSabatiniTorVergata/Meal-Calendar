package com.example.mealcalendar.findrecipe;

public class RecipeDeletionException extends Exception {
    public RecipeDeletionException(String message) {
        super(message);
    }

    public RecipeDeletionException(String message, Throwable cause) {
        super(message, cause);
    }
}
