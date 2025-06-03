package com.example.mealcalendar.seteatingtime;

import jakarta.mail.MessagingException;

import java.io.IOException;

public class RestaurantMailObserver implements MailObserver {
    private final MailService mailService;

    public RestaurantMailObserver(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public void sendConfirmation(MealcalendarBean bean) throws MessagingException, IOException {
        String subject = "Email di conferma posto dove mangiare";
        String body = String.format("Hai deciso di mangiare a: %s il giorno: %s alle ore: %s",
                bean.getScelta(), bean.getData(), bean.getOra());
        mailService.send(bean.getUser(), subject, body);
    }

    @Override
    public void sendReminder(MealcalendarBean bean) throws MessagingException, IOException {
        String subject = "Ricordo della tua scelta";
        String body = String.format("Ti ricordo che hai scelto di mangiare a %s tra mezz'ora.",
                bean.getScelta());
        mailService.send(bean.getUser(), subject, body);
    }
}
