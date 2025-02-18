package com.example.mealcalendar;


import java.util.logging.Logger;
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

import static com.example.mealcalendar.MealCalenderViewBoundary.sceltaLuogo;

public class MealcalendarController {

    private static final Logger LOGGER = Logger.getLogger(MealcalendarController.class.getName());

    private MealcalendarBean mealcalendarBean;

    public MealcalendarController(MealcalendarBean bean) {
        this.mealcalendarBean = bean;

    }

    public String getMail() throws Exception {
        UserDao dao = new UserDao(false, false);
        UserEntity user = dao.getUserByUsername(mealcalendarBean.getUser());
        return user.getEmail();
    }

    public void invioMail() throws Exception {

        String mail = getMail();

        final String user = "smithvalenzuela324@gmail.com"; // La tua email
        final String password = "kbibyuksfhchryvs"; // La tua password o una password dell'app (se 2FA è abilitato)

        // Impostazione delle proprietà del server SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Usato per STARTTLS
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");// Porta SMTP per TLS
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");


        // Ottieni una sessione con l'autenticazione
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        if (sceltaLuogo) {
            try {
                // Creare il messaggio
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(user)); // L'indirizzo del mittente
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail)); // L'indirizzo del destinatario
                message.setSubject("Email di conferma posto dove mangiare"); // Oggetto dell'email
                message.setText("Salve,hai appena deciso di mangiare a: " + mealcalendarBean.getScelta() + " il giorno: " + mealcalendarBean.getData() + " alle ore: " + mealcalendarBean.getOra()); // Corpo dell'email

                // Invia il messaggio
                Transport.send(message);

                LOGGER.info("Email sent successfully");

                sendEmailProgrammataRistorante();

            } catch (MessagingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                // Creare il messaggio
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(user)); // L'indirizzo del mittente
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail)); // L'indirizzo del destinatario
                message.setSubject("Conferma Posto dove mangiare"); // Oggetto dell'email
                message.setText("Salve,la ricetta scelta da te per il giorno: " + mealcalendarBean.getData() + " alle ore: " + mealcalendarBean.getOra() + " è: " + mealcalendarBean.getScelta()); // Corpo dell'email

                // Invia il messaggio
                Transport.send(message);

                LOGGER.info("Email sent successfully");

                sendEmailProgrammataRicetta();

            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }


    public void sendEmailProgrammataRicetta() throws Exception {

        String mail = getMail();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime ora = LocalTime.parse(mealcalendarBean.getOra(), formatter);

        // Combinare LocalDate e LocalTime in LocalDateTime
        LocalDateTime dataOra = LocalDateTime.of(mealcalendarBean.getData(), ora);
        LocalDateTime oraDiInvio = dataOra.minusMinutes(30);

        // Calcola il ritardo in millisecondi
        long delay = ChronoUnit.MILLIS.between(LocalDateTime.now(), oraDiInvio);

        if (delay > 0) {
            // Pianifica l'invio
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.schedule(() -> {
                try {

                    Properties properties = System.getProperties();
                    properties.put("mail.smtp.host", "smtp.gmail.com");
                    properties.put("mail.smtp.port", "587");
                    properties.put("mail.smtp.starttls.enable", "true");
                    properties.put("mail.smtp.auth", "true");

                    String user = "smithvalenzuela324@gmail.com"; // Sostituisci con la tua email
                    String password = "kbibyuksfhchryvs"; // Sostituisci con la tua password

                    Session session = Session.getInstance(properties, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(user, password);
                        }
                    });

                    // Creare il messaggio da inviare
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(user));
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
                    message.setSubject("Ricordo della tua scelta");
                    message.setText("Ti ricordo che hai scelto di fare la ricetta " + mealcalendarBean.getScelta() + " tra mezz'ora ");

                    // Inviare la mail
                    Transport.send(message);
                    LOGGER.info("Email inviata!");

                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }, delay, TimeUnit.MILLISECONDS);
        } else {
            LOGGER.info("L'orario selezionato è già passato!");
        }
    }

    public void sendEmailProgrammataRistorante() throws Exception {

        String mail = getMail();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime ora = LocalTime.parse(mealcalendarBean.getOra(), formatter);

        // Combinare LocalDate e LocalTime in LocalDateTime
        LocalDateTime dataOra = LocalDateTime.of(mealcalendarBean.getData(), ora);
        LocalDateTime oraDiInvio = dataOra.minusMinutes(30);

        // Calcola il ritardo in millisecondi
        long delay = ChronoUnit.MILLIS.between(LocalDateTime.now(), oraDiInvio);

        if (delay > 0) {
            // Pianifica l'invio
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.schedule(() -> {
                try {

                    Properties properties = System.getProperties();
                    properties.put("mail.smtp.host", "smtp.gmail.com");
                    properties.put("mail.smtp.port", "587");
                    properties.put("mail.smtp.starttls.enable", "true");
                    properties.put("mail.smtp.auth", "true");

                    String user = "smithvalenzuela324@gmail.com"; // Sostituisci con la tua email
                    String password = "kbibyuksfhchryvs"; // Sostituisci con la tua password

                    Session session = Session.getInstance(properties, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(user, password);
                        }
                    });

                    // Creare il messaggio da inviare
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(user));
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
                    message.setSubject("Ricordo della tua scelta");
                    message.setText("Ti ricordo che hai scelto di mangiare a " + mealcalendarBean.getScelta() + " tra mezz'ora ");

                    // Inviare la mail
                    Transport.send(message);

                    LOGGER.info("Email inviata!");

                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }, delay, TimeUnit.MILLISECONDS);
        } else {
            LOGGER.info("L'orario selezionato è già passato!");
        }

    }
}
