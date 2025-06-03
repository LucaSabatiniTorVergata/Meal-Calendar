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
        String user=bean.getUser();
        sendTemplate(user,bean,"Email di conferma ricetta scelta", "Hai deciso di mangiare la ricetta : %s il giorno: %s alle ore: %s");
    }

    @Override
    public void sendReminder(MealcalendarBean bean) throws MessagingException, IOException {
        String user=bean.getUser();
        sendTemplate(user,bean, "Ricordo della tua scelta", "Ti ricordo che hai scelto di mangiare :s%s tra mezz'ora.");
    }

    private void sendTemplate(String user,MealcalendarBean bean, String subject, String body) throws MessagingException, IOException {
        String nbody=String.format(body,bean.getScelta(), bean.getData(), bean.getOra());
        mailService.send(user,subject,nbody);
    }
}
