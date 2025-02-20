package com.example.mealcalendar;

public class EncryptionRuntimeException extends RuntimeException {
    public EncryptionRuntimeException(String message) {
        super(message);
    }

    public EncryptionRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}