package com.example.mealcalendar;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class MailController {

    // CONFIGURAZIONE MAILGUN
    private static final String SMTP_SERVER = "smtp.mailgun.org";
    private static final String USERNAME = "tsandbox2dc705909af84722aac8e3e2b78ad86a.mailgun.org"; // Es: postmaster@sandbox123.mailgun.org
    private static final String PASSWORD = "a8256abcdbaf56f9938552ea466226ea-1654a412-6e830598";
    private static final String FROM_EMAIL = "postmaster@sandbox2dc705909af84722aac8e3e2b78ad86a.mailgun.org";

    public void sendEmail(String to, String subject, String bodyText) {

        // Configurazione delle proprietà SMTP
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", SMTP_SERVER);
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", SMTP_SERVER);

        // Creazione della sessione
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            // Composizione del messaggio
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(bodyText);

            // Invio dell'email
            Transport.send(message);
            System.out.println("Email inviata con successo a: " + to);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
