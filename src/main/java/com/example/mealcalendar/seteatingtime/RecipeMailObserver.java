package com.example.mealcalendar.seteatingtime;

import jakarta.mail.MessagingException;

import java.io.IOException;

public class RecipeMailObserver implements MailObserver {
    private final MailService mailService;

    public RecipeMailObserver(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public void sendConfirmation(MealcalendarBean bean) throws MessagingException, IOException {
        String subject = "Email di conferma ricetta scelta";
        String body = String.format("Hai deciso di mangiare la ricetta : %s il giorno: %s alle ore: %s",
                bean.getScelta(), bean.getData(), bean.getOra());
        mailService.send(bean.getUser(), subject, body);
    }

    @Override
    public void sendReminder(MealcalendarBean bean) throws MessagingException, IOException {
        String subject = "Ricordo della tua scelta";
        String body = String.format("Ti ricordo che hai scelto di mangiare :s%s tra mezz'ora.",
                bean.getScelta());
        mailService.send(bean.getUser(), subject, body);
    }
}
