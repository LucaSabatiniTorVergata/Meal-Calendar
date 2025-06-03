package com.example.mealcalendar.seteatingtime;

import java.io.IOException;


import jakarta.mail.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MealcalendarController {
    Logger logger = Logger.getLogger(getClass().getName());


    private final MealcalendarBean bean;
    private final MailObserver mailObserver;

    public MealcalendarController(MealcalendarBean bean) {
        this.bean = bean;
        MailService service = new MailService();
        this.mailObserver = MealCalenderViewController.isSceltaLuogo()
                ? new RestaurantMailObserver(service)
                : new RecipeMailObserver(service);
    }

    public void invioMail() {
        try {
            mailObserver.sendConfirmation(bean);
            sendReminder();
        } catch (Exception e) {
            // log errore
        }
    }

    private void sendReminder() {
        // stessa logica di delay con scheduledExecutor
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.schedule(() -> {
            try {
                mailObserver.sendReminder(bean);
            } catch (MessagingException | IOException e) {
                // log errore
            }
        }, calculateDelay(), TimeUnit.MILLISECONDS);
        scheduler.shutdown();

    }

    private long calculateDelay() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime oraScelta = LocalTime.parse(bean.getOra(), formatter);
        LocalDateTime dataOraScelta = LocalDateTime.of(bean.getData(), oraScelta);
        LocalDateTime oraInvio = dataOraScelta.minusMinutes(30);

        long delayMillis = ChronoUnit.MILLIS.between(LocalDateTime.now(), oraInvio);
        return Math.max(delayMillis, 0); // Evita valori negativi
    }

    public static class UserNotFoundException extends Exception {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

}


