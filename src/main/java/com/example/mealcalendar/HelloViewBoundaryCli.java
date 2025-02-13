package com.example.mealcalendar;

import com.example.mealcalendar.UserBean;
import com.example.mealcalendar.UserDaoFactory;
import com.example.mealcalendar.UserDaoInterface;
import com.example.mealcalendar.RegisterController;
import com.example.mealcalendar.LoginController;
import com.example.mealcalendar.LoginBean;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloViewBoundaryCli {
    private final Scanner scanner = new Scanner(System.in);
    private final CliController cliController = new CliController();
    private static final Logger LOGGER = Logger.getLogger(HelloViewBoundaryCli.class.getName());

    public void start() {
        System.out.println("===== Calendario dei Pasti (CLI) =====");
        System.out.println("1. Registrazione");
        System.out.println("2. Login (se già registrato)");
        System.out.println("3. Entra come Guest");
        System.out.print("Scegli un'opzione: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                register();
                break;
            case "2":
                cliController.navigateTo("login");
                break;
            case "3":
                cliController.navigateTo("guest");
                break;
            default:
                System.out.println("❌ Scelta non valida.");
                start();
                break;
        }
    }

    public void register() {
        System.out.println("\n===== Registrazione =====");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.out.println("❌ Completa tutti i campi!");
            return;
        }

        UserDaoInterface userDao = UserDaoFactory.createUserDao();
        RegisterController controller = new RegisterController(userDao);

        UserBean userRegisterBean = new UserBean(username, email, password);
        try {
            boolean result = controller.register(userRegisterBean);

            if (result) {
                System.out.println("✅ Registrazione completata!");
                cliController.navigateTo("login");
            } else {
                System.out.println("❌ Username già esistente.");
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Errore durante la registrazione.", e);
            System.out.println("❌ Errore durante la registrazione.");
        }
    }
}
