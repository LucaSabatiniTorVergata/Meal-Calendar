package com.example.mealcalendar;

/**
 * Eccezione personalizzata lanciata quando si verifica un errore durante l'invio della mail.
 */
public class MailSendingException extends Exception {
    public MailSendingException(String message) {
        super(message);
    }

    public MailSendingException(String message, Throwable cause) {
        super(message, cause);
    }
}
