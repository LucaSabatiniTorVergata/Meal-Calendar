package com.example.mealcalendar;

import java.io.IOException;
import java.util.logging.*;
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
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String EMAIL_SENDER = "smithvalenzuela324@gmail.com";
    private static final String EMAIL_PASSWORD = System.getenv("EMAIL_PASSWORD"); // Usa variabili d'ambiente
    private static final String DATE_TIME_PATTERN = "HH:mm";
    private static final int REMINDER_MINUTES = 30;

    private final MealcalendarBean mealcalendarBean;

    static {
        // Configura il logger con un formatter standard
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(consoleHandler);
    }

    public MealcalendarController(MealcalendarBean bean) {
        this.mealcalendarBean = bean;
    }

    public String getMail() throws UserNotFoundException, IOException {
        UserDao dao = new UserDao(false, false);
        UserEntity user = dao.getUserByUsername(mealcalendarBean.getUser());
        if (user == null || user.getEmail() == null) {
            throw new UserNotFoundException("User email not found");
        }
        return user.getEmail();
    }

    public void invioMail() {
        try {
            String mail = getMail();
            String subject;
            String messageBody;

            if (sceltaLuogo) {
                subject = "Email di conferma posto dove mangiare";
                messageBody = String.format("Salve, hai deciso di mangiare a: %s il giorno: %s alle ore: %s", mealcalendarBean.getScelta(), mealcalendarBean.getData(), mealcalendarBean.getOra());
                sendEmail(mail, subject, messageBody);
                sendEmailProgrammata(true);
            } else {
                subject = "Conferma Posto dove mangiare";
                messageBody = String.format("Salve, la ricetta scelta da te per il giorno: %s alle ore: %s è: %s", mealcalendarBean.getData(), mealcalendarBean.getOra(), mealcalendarBean.getScelta());
                sendEmail(mail, subject, messageBody);
                sendEmailProgrammata(false);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore nell''invio della mail: {0}", e.getMessage());
        }
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
                // Schedule the task
                scheduler.schedule(() -> {
                    try {
                        String subject = "Ricordo della tua scelta";
                        String body = String.format("Ti ricordo che hai scelto di %s %s tra mezz'ora.", isRestaurant ? "mangiare a" : "preparare la ricetta", mealcalendarBean.getScelta());
                        sendEmail(mail, subject, body);
                    } catch (MessagingException e) {
                        LOGGER.log(Level.SEVERE, "Errore nell'invio dell'email programmata: {0}", e.getMessage());
                    }
                }, delay, TimeUnit.MILLISECONDS);

                // Gracefully shut down the scheduler after the task is done
                scheduler.shutdown();
            } else {
                LOGGER.log(Level.INFO, "L'orario selezionato è già passato!");
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
