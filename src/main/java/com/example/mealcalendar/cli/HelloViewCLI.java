package com.example.mealcalendar.cli;

import com.example.mealcalendar.PersistenceType;
import com.example.mealcalendar.SessionManagerSLT;

import java.util.Scanner;


public class HelloViewCLI {

    private final Scanner scanner;

    public HelloViewCLI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        System.out.println("Avvio CLI...");
        // === Selezione tipo di persistenza ===
        System.out.println("=== Seleziona modalità di persistenza ===");
        System.out.println("1. Database");
        System.out.println("2. File System");
        System.out.println("3. RAM: ");
        String persistenceChoice = scanner.nextLine().trim();

        switch (persistenceChoice) {
            case "1":
                SessionManagerSLT.getInstance().setPersistenceType(PersistenceType.DATABASE);
                System.out.println("Modalità: Database attiva");
                break;
            case "2":
                SessionManagerSLT.getInstance().setPersistenceType(PersistenceType.FILESYSTEM);
                System.out.println("Modalità: File System attiva");
                break;
            case "3":
                SessionManagerSLT.getInstance().setPersistenceType(PersistenceType.RAM);
                System.out.println("Modalità: RAM attiva");
                break;
            default:
                System.out.println("Scelta non valida, default RAM.");
                SessionManagerSLT.getInstance().setPersistenceType(PersistenceType.RAM);
        }

        // === Menu principale ===
        System.out.println("\n=== Menu Principale ===");
        System.out.println("1. Registrati come Utente");
        System.out.println("2. Registrati come Nutrizionista");
        System.out.println("3. Registrati come Ristorante");
        System.out.println("4. Login");
        String roleChoice = scanner.nextLine().trim();

        switch (roleChoice) {
            case "1":
                SessionManagerSLT.getInstance().setRuolo("user");
                new RegisterCLI(scanner).start();
                break;
            case "2":
                SessionManagerSLT.getInstance().setRuolo("nutritionist");
                new RegisterCLI(scanner).start();
                break;
            case "3":
                SessionManagerSLT.getInstance().setRuolo("restaurant");
                new RegisterCLI(scanner).start();
                break;
            case "4":
                new LoginCLI(scanner).start();
                break;
            default:
                System.out.println("Scelta non valida, ritorno al menu principale.");
                start();
        }
    }
}
