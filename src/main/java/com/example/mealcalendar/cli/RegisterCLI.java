package com.example.mealcalendar.cli;

import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.register_login.RegistrationController;
import com.example.mealcalendar.register_login.UserBeanA;

import java.util.Scanner;


public class RegisterCLI {

    private final Scanner scanner;

    public RegisterCLI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {

        System.out.println("=== Registrazione Nuovo Utente ===");

        System.out.println("Inserisci username:");
        String username = scanner.nextLine().trim();

        System.out.println("Inserisci email:");
        String email = scanner.nextLine().trim();

        System.out.println("Inserisci password:");
        String password = scanner.nextLine().trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.out.println("Compila tutti i campi.");
            return;
        }

        UserBeanA bean = new UserBeanA(username, email, password, SessionManagerSLT.getInstance().getRuolo());
        RegistrationController regController = new RegistrationController();

        try {
            regController.register(bean);
            System.out.println("Registrazione completata!");
        } catch (Exception e) {
            System.out.println("Errore nella registrazione: " + e.getMessage());
        }

        new HelloViewCLI(scanner).start();
    }
}
