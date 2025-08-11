package com.example.mealcalendar.cli;

import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.register_login.LoginController;
import com.example.mealcalendar.register_login.UserLoginBean;

import java.util.Scanner;
import java.util.logging.Logger;

public class LoginCLI {

    private static final Logger logger = Logger.getLogger(LoginCLI.class.getName());
    private Scanner scanner;

    public LoginCLI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {

        logger.info("=== Login ===");

        logger.info("Inserisci username:");
        String username = scanner.nextLine().trim();

        logger.info("Inserisci password:");
        String password = scanner.nextLine().trim();

        logger.info("Scegli il ruolo:");
        logger.info("1 - Utente");
        logger.info("2 - Nutrizonista");
        logger.info("3 - Ristorante");

        String roleChoice = scanner.nextLine().trim();
        String selectedRole = "";

        switch (roleChoice) {
            case "1":
                selectedRole = "user";
                break;
            case "2":
                selectedRole = "nutritionist";
                break;
            case "3":
                selectedRole = "restaurant";
                break;
            default:
                logger.warning("Scelta ruolo non valida.");
                return;
        }

        if (username.isEmpty() || password.isEmpty() || selectedRole.isEmpty()) {
            logger.warning("Compila tutti i campi.");
            return;
        }

        UserLoginBean bean = new UserLoginBean(username, password, selectedRole);
        LoginController controller = new LoginController();

        try {
            if (controller.vallogin(bean)) {
                logger.info("Login effettuato!");

                SessionManagerSLT.getInstance().setRuolo(selectedRole);

                if (bean.getRuolo().equalsIgnoreCase("restaurant")) {
                    logger.info("Apertura interfaccia gestione ristorante...");
                   // new RestaurantManagementCLI().start();
                } else {
                    logger.info("Apertura interfaccia menu...");
                    new MainMenuCLI(scanner).start();
                }
            } else {
                logger.warning("Credenziali non valide.");
            }
        } catch (Exception e) {
            logger.severe("Errore durante il login: " + e.getMessage());
        }
    }
}
