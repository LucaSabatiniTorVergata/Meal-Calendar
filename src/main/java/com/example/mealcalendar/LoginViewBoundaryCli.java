package com.example.mealcalendar;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginViewBoundaryCli {
    private final Scanner scanner = new Scanner(System.in);
    private static final Logger LOGGER = Logger.getLogger(LoginViewBoundaryCli.class.getName());
    private final CliController cliController = new CliController();

    public void start() {
        System.out.println("\n===== Login =====");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        LoginBean userLoginBean = new LoginBean(username, password);
        LoginController controller = new LoginController();

        try {
            boolean result = controller.login(userLoginBean);
            if (result) {
                System.out.println("✅ Login avvenuto con successo!");
                cliController.navigateTo("mainmenu");
            } else {
                System.out.println("❌ Credenziali errate.");
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Errore durante il login.", e);
            System.out.println("❌ Errore durante il login.");
        }
    }
}
