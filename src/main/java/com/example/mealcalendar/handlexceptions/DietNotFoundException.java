package com.example.mealcalendar.handlexceptions;

public class DietNotFoundException extends Exception {
    public DietNotFoundException(String message) {
        super(message);
    }
}
