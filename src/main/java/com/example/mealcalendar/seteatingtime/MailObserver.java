package com.example.mealcalendar.seteatingtime;

import jakarta.mail.MessagingException;

import java.io.IOException;

public interface MailObserver {
    void sendConfirmation(MealcalendarBean bean) throws MessagingException, IOException;
    void sendReminder(MealcalendarBean bean) throws MessagingException, IOException;
}
