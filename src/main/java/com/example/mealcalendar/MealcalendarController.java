package com.example.mealcalendar;



import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.mealcalendar.MealCalenderViewBoundary.sceltaLuogo;

public class MealcalendarController {

    private MealcalendarBean mealcalendarBean;

    public MealcalendarController(MealcalendarBean bean) {
        this.mealcalendarBean = bean;

    }

    public String getMail() throws Exception {
        UserDao dao = new UserDao(false);
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

        if(sceltaLuogo) {
            try {
                // Creare il messaggio
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(user)); // L'indirizzo del mittente
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("leo.polidori99@gmail.com")); // L'indirizzo del destinatario
                message.setSubject("Email di coonferma posto dove mangiare"); // Oggetto dell'email
                message.setText("Salve,hai appena deciso di mangiare a" + mealcalendarBean.getScelta() + "il giorno " + mealcalendarBean.getData() + "alle ore" + mealcalendarBean.getOra()); // Corpo dell'email

                // Invia il messaggio
                Transport.send(message);

                System.out.println("Email sent successfully");

            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                // Creare il messaggio
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(user)); // L'indirizzo del mittente
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("leo.polidori99@gmail.com")); // L'indirizzo del destinatario
                message.setSubject("Conferma Posto dove mangiare"); // Oggetto dell'email
                message.setText("Salve,la ricetta scelta da te per il giorno" + mealcalendarBean.getData() + "alle ore" + mealcalendarBean.getOra() + "è" + mealcalendarBean.getScelta()); // Corpo dell'email

                // Invia il messaggio
                Transport.send(message);

                System.out.println("Email sent successfully");

            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    };
}
