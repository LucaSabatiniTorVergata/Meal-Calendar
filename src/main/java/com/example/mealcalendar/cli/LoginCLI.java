package com.example.mealcalendar.cli;

import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.register_login.LoginController;
import com.example.mealcalendar.register_login.UserLoginBean;

import java.util.Scanner;


public class LoginCLI {


    private Scanner scanner;

    public LoginCLI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {

        System.out.println("=== Login ===");

        System.out.println("Inserisci username:");
        String username = scanner.nextLine().trim();

        System.out.println("Inserisci password:");
        String password = scanner.nextLine().trim();

        System.out.println("Scegli il ruolo:");
        System.out.println("1 - Utente");
        System.out.println("2 - Nutrizonista");
        System.out.println("3 - Ristorante");

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
                System.out.println("Scelta ruolo non valida.");
                return;
        }

        if (username.isEmpty() || password.isEmpty() || selectedRole.isEmpty()) {
            System.out.println("Compila tutti i campi.");
            return;
        }

        UserLoginBean bean = new UserLoginBean(username, password, selectedRole);
        LoginController controller = new LoginController();

        try {
            if (controller.vallogin(bean)) {
                System.out.println("Login effettuato!");

                SessionManagerSLT.getInstance().setRuolo(selectedRole);

                if (bean.getRuolo().equalsIgnoreCase("restaurant")) {
                    System.out.println("Apertura interfaccia gestione ristorante...");// qui va il ristoranteCLI
                } else {
                    System.out.println("Apertura interfaccia menu...");
                    new MainMenuCLI(scanner).start();
                }
            } else {
                System.out.println("Credenziali non valide.");
            }
        } catch (Exception e) {
            System.out.println("Errore durante il login: " + e.getMessage());
        }
    }
}
