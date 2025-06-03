package com.example.mealcalendar.seteatingtime;

import com.example.mealcalendar.login.UserDaoFactory;
import com.example.mealcalendar.login.UserDaoInterface;
import com.example.mealcalendar.login.UserEntity;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MailService {

    Logger logger = Logger.getLogger(getClass().getName());
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String EMAIL_SENDER = "mealcalendarog@gmail.com";
    private static final String EMAIL_PASSWORD = System.getenv("EMAIL_PASSWORD");// Usa variabili d'ambiente
    private UserDaoInterface userDAO;



    public void send(String user, String subject, String body) throws MessagingException, IOException {

        this.userDAO=UserDaoFactory.createUserDao();
        UserEntity entity = userDAO.getUserByUsername(user);

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
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(entity.getEmail()));
            message.setSubject(subject);
            message.setText(body);
            transport.connect(SMTP_HOST, EMAIL_SENDER, EMAIL_PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
        }
        catch (MessagingException e) {
            logger.log(Level.SEVERE, "Errore nell'invio dell'email: {0}", e.getMessage());
        }
    }
}