package com.example.mealcalendar.login;

import com.example.mealcalendar.AntiCodeSmellPrinter;
import com.example.mealcalendar.CliController;
import com.example.mealcalendar.SessionManagerSLT;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginViewControllerCli {
    private final Scanner scanner = new Scanner(System.in);
    private static final Logger LOGGER = Logger.getLogger(LoginViewControllerCli.class.getName());
    private final CliController cliController = new CliController();
    private final AntiCodeSmellPrinter printer = new AntiCodeSmellPrinter("LoginView");

    public void start() throws Exception {
        while (true) {
            printer.print("\n===== Login =====");
            printer.print("Username: ");
            String username = scanner.nextLine();
            printer.print("Password: ");
            String password = scanner.nextLine();
            printer.print("\nScegli un'opzione:");
            printer.print("1. Login");
            printer.print("2. Torna indietro");
            printer.print("0. Esci");
            printer.print("Opzione: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    // Se l'utente ha scelto Login
                    LoginBean userLoginBean = new LoginBean(username, password);
                    LoginController controller = new LoginController();

                    try {
                        boolean result = controller.login(userLoginBean);
                        if (result) {
                            SessionManagerSLT.getInstance().setLoggedInUsername(userLoginBean.getUsername());
                            printer.print("✅ Login avvenuto con successo!");
                            cliController.navigateTo("mainmenu");
                            return;  // Dopo il login, torna al menu principale e termina il metodo
                        } else {
                            printer.print("❌ Credenziali errate.");
                        }
                    } catch (Exception e) {
                        LOGGER.log(Level.SEVERE, "Errore durante il login.", e);
                        printer.print("❌ Errore durante il login.");
                    }
                }
                case "2" -> {
                    cliController.navigateTo("hello");
                    return;  // Torna alla Home e termina il metodo
                }
                case "0" -> {
                    printer.print("👋 Uscita dal programma...");
                    System.exit(0);
                }
                default -> printer.print("Opzione non valida, prova di nuovo.");
            }
        }
    }
}
