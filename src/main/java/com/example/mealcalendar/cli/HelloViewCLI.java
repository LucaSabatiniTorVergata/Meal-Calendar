package com.example.mealcalendar.cli;

import com.example.mealcalendar.PersistenceType;
import com.example.mealcalendar.SessionManagerSLT;

import java.util.Scanner;
import java.util.logging.Logger;

public class HelloViewCLI {

    private static final Logger logger = Logger.getLogger(HelloViewCLI.class.getName());
    private final Scanner scanner;

    public HelloViewCLI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        logger.info("Avvio CLI...");
        // === Selezione tipo di persistenza ===
        logger.info("=== Seleziona modalità di persistenza ===");
        logger.info("1. Database");
        logger.info("2. File System");
        logger.info("3. RAM");
        String persistenceChoice = scanner.nextLine().trim();

        switch (persistenceChoice) {
            case "1":
                SessionManagerSLT.getInstance().setPersistenceType(PersistenceType.DATABASE);
                logger.info("Modalità: Database attiva");
                break;
            case "2":
                SessionManagerSLT.getInstance().setPersistenceType(PersistenceType.FILESYSTEM);
                logger.info("Modalità: File System attiva");
                break;
            case "3":
                SessionManagerSLT.getInstance().setPersistenceType(PersistenceType.RAM);
                logger.info("Modalità: RAM attiva");
                break;
            default:
                logger.warning("Scelta non valida, default RAM.");
                SessionManagerSLT.getInstance().setPersistenceType(PersistenceType.RAM);
        }

        // === Menu principale ===
        logger.info("\n=== Menu Principale ===");
        logger.info("1. Registrati come Utente");
        logger.info("2. Registrati come Nutrizionista");
        logger.info("3. Registrati come Ristorante");
        logger.info("4. Login");
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
                logger.warning("Scelta non valida, ritorno al menu principale.");
                start();
        }
    }
}
