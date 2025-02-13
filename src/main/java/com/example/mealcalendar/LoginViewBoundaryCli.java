package com.example.mealcalendar;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginViewBoundaryCli {
    private final Scanner scanner = new Scanner(System.in);
    private static final Logger LOGGER = Logger.getLogger(LoginViewBoundaryCli.class.getName());
    private final CliController cliController = new CliController();

    public void start() {
        while (true) {
            System.out.println("\n===== Login =====");
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();
            System.out.println("Scegli un'opzione:");
            System.out.println("1. Login");
            System.out.println("2. Torna indietro");
            System.out.print("Opzione: ");
            String choice = scanner.nextLine();

            if ("2".equals(choice)) {
                cliController.navigateTo("registration");
                return;  // Torna alla Home e termina il metodo
            }

            if ("1".equals(choice)) {
                // Se l'utente ha scelto Login
                LoginBean userLoginBean = new LoginBean(username, password);
                LoginController controller = new LoginController();

                try {
                    boolean result = controller.login(userLoginBean);
                    if (result) {
                        SessionManagerSLT.getInstance().setLoggedInUsername(userLoginBean.getUsername());
                        System.out.println("✅ Login avvenuto con successo!");
                        cliController.navigateTo("mainmenu");
                        return;  // Dopo il login, torna al menu principale e termina il metodo
                    } else {
                        System.out.println("❌ Credenziali errate.");
                    }
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Errore durante il login.", e);
                    System.out.println("❌ Errore durante il login.");
                }
            } else {
                System.out.println("Opzione non valida, prova di nuovo.");
            }
        }
    }
}
