package com.example.mealcalendar.seteatingtime;

import java.io.IOException;
import java.util.logging.*;


import com.example.mealcalendar.login.UserDaoFactory;
import com.example.mealcalendar.login.UserDaoInterface;
import com.example.mealcalendar.login.UserEntity;
import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MealcalendarController {

    private static final Logger LOGGER = Logger.getLogger(MealcalendarController.class.getName());
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String EMAIL_SENDER = "mealcalendarog@gmail.com";
    private static final String EMAIL_PASSWORD = System.getenv("EMAIL_PASSWORD");// Usa variabili d'ambiente
    private static final String DATE_TIME_PATTERN = "HH:mm";
    private static final int REMINDER_MINUTES = 30;
    private UserDaoInterface userDAO;

    private final MealcalendarBean mealcalendarBean;

    static {
        // Configura il logger con un formatter standard
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(consoleHandler);
    }

    public MealcalendarController(MealcalendarBean bean) {
        this.mealcalendarBean = bean;
        this.userDAO=UserDaoFactory.createUserDao();
    }



    public void invioMail() {
        try {
            String mail = getMail();
            boolean isRestaurant = MealCalenderViewController.isSceltaLuogo();
            String subject = isRestaurant ? "Email di conferma posto dove mangiare" : "Conferma Posto dove mangiare";
            String messageBody = isRestaurant ?
                    String.format("Salve, hai deciso di mangiare a: %s il giorno: %s alle ore: %s", mealcalendarBean.getScelta(), mealcalendarBean.getData(), mealcalendarBean.getOra()) :
                    String.format("Salve, la ricetta scelta da te per il giorno: %s alle ore: %s è: %s", mealcalendarBean.getData(), mealcalendarBean.getOra(), mealcalendarBean.getScelta());
            sendEmail(mail, subject, messageBody);
            sendEmailProgrammata(isRestaurant);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore nell''invio della mail: {0}", e.getMessage());
        }
    }

    public String getMail() throws UserNotFoundException, IOException {
        UserEntity user = userDAO.getUserByUsername(mealcalendarBean.getUser());

        if (user == null || user.getEmail() == null) {
            throw new UserNotFoundException("User email not found");
        }
        return user.getEmail();
    }

    private void sendEmail(String recipient, String subject, String body) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.ssl.trust", SMTP_HOST);

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_SENDER, EMAIL_PASSWORD);
            }
        });

        try (Transport transport = session.getTransport("smtp")) {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_SENDER));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(body);
            transport.connect(SMTP_HOST, EMAIL_SENDER, EMAIL_PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
        }
        LOGGER.log(Level.INFO, "Email inviata con successo a {0}", recipient);
    }

    private void sendEmailProgrammata(boolean isRestaurant) {
        try {
            String mail = getMail();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
            LocalTime ora = LocalTime.parse(mealcalendarBean.getOra(), formatter);
            LocalDateTime dataOra = LocalDateTime.of(mealcalendarBean.getData(), ora);
            LocalDateTime oraDiInvio = dataOra.minusMinutes(REMINDER_MINUTES);

            long delay = ChronoUnit.MILLIS.between(LocalDateTime.now(), oraDiInvio);
            if (delay > 0) {
                ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
                scheduler.schedule(() -> {
                    try {
                        String subject = "Ricordo della tua scelta";
                        String body = String.format("Ti ricordo che hai scelto di %s %s tra mezz'ora.", isRestaurant ? "mangiare a" : "preparare la ricetta", mealcalendarBean.getScelta());
                        sendEmail(mail, subject, body);
                    } catch (MessagingException e) {
                        LOGGER.log(Level.SEVERE, "Errore nell'invio dell'email programmata: {0}", e.getMessage());
                    }
                }, delay, TimeUnit.MILLISECONDS);
                scheduler.shutdown();
            } else {
                LOGGER.log(Level.INFO, "L''orario selezionato è già passato!");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore nella programmazione dell''email: {0}", e.getMessage());
        }
    }

    public static class UserNotFoundException extends Exception {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
}