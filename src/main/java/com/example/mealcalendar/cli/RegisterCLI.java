package com.example.mealcalendar.cli;

import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.register_login.RegistrationController;
import com.example.mealcalendar.register_login.UserBeanA;
import com.example.mealcalendar.model.TipologiaRistorante;

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

        // Creo bean utente base
        UserBeanA bean = new UserBeanA(username, email, password, SessionManagerSLT.getInstance().getRuolo());
        RegistrationController regController = new RegistrationController();

        try {
            // Se il ruolo è "restaurant" chiedo la tipologia
            if ("restaurant".equalsIgnoreCase(SessionManagerSLT.getInstance().getRuolo())) {
                System.out.println("Seleziona tipologia (VEGANO, VEGETARIANO, ONNIVORO):");
                String tipologiaInput = scanner.nextLine().trim().toUpperCase();

                TipologiaRistorante tipologia;
                try {
                    tipologia = TipologiaRistorante.valueOf(tipologiaInput);
                } catch (IllegalArgumentException e) {
                    System.out.println("Tipologia non valida, default ONNIVORO.");
                    tipologia = TipologiaRistorante.ONNIVORO;
                }

                regController.register(bean, tipologia);
            } else {
                // per utenti normali
                regController.register(bean);
            }

            System.out.println("Registrazione completata!");
        } catch (Exception e) {
            System.out.println("Errore nella registrazione: " + e.getMessage());
        }

        new HelloViewCLI(scanner).start();
    }
}
