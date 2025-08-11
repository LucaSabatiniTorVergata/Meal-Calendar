package com.example.mealcalendar.cli;

import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.register_login.RegistrationController;
import com.example.mealcalendar.register_login.UserBeanA;

import java.util.Scanner;
import java.util.logging.Logger;

public class RegisterCLI {

    private static final Logger logger = Logger.getLogger(RegisterCLI.class.getName());
    private final Scanner scanner;

    public RegisterCLI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {

        logger.info("=== Registrazione Nuovo Utente ===");

        logger.info("Inserisci username:");
        String username = scanner.nextLine().trim();

        logger.info("Inserisci email:");
        String email = scanner.nextLine().trim();

        logger.info("Inserisci password:");
        String password = scanner.nextLine().trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            logger.warning("Compila tutti i campi.");
            return;
        }

        UserBeanA bean = new UserBeanA(username, email, password, SessionManagerSLT.getInstance().getRuolo());
        RegistrationController regController = new RegistrationController();

        try {
            regController.register(bean);
            logger.info("Registrazione completata!");
        } catch (Exception e) {
            logger.severe("Errore nella registrazione: " + e.getMessage());
        }

        new HelloViewCLI(scanner).start();
    }
}
