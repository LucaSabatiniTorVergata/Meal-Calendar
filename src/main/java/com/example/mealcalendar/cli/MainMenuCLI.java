package com.example.mealcalendar.cli;

import com.example.mealcalendar.SessionManagerSLT;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

public class MainMenuCLI {

    private static final Logger logger = Logger.getLogger(MainMenuCLI.class.getName());
    private final Scanner scanner;

    public MainMenuCLI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        while (true) {
            String username = SessionManagerSLT.getInstance().getLoggedInUsername();
            String ruolo = SessionManagerSLT.getInstance().getLoggedRole();

            logger.info("===== Menu Principale =====");
            if (username != null) {
                logger.info("Ciao, " + username + "!");
            }
            if (ruolo != null) {
                logger.info("Sei un " + ruolo);
            }

            if (!showOptions(ruolo)) {
                logger.warning("Ruolo non riconosciuto o utente non loggato.");
                return;
            }

            String scelta = scanner.nextLine().trim();
            try {
                handleChoice(ruolo, scelta);
            } catch (Exception e) {
                logger.severe("Errore: " + e.getMessage());
            }
        }
    }

    private boolean showOptions(String ruolo) {
        switch (ruolo) {
            case "user" -> {
                logger.info("1. Segui dieta");
                logger.info("2. Inserisci pasti");
                logger.info("3. Visualizza report");
                logger.info("4. Cerca ristorante");
                logger.info("5. Visualizza risultati");
                logger.info("6. Logout");
            }
            case "nutritionist" -> {
                logger.info("1. Crea dieta");
                logger.info("2. Visualizza richieste");
                logger.info("3. Logout");
            }
            case "restaurant" -> {
                logger.info("1. Cerca ristorante (guest)");
                logger.info("2. Logout");
            }
            default -> {
                return false;
            }
        }
        return true;
    }

    private void handleChoice(String ruolo, String scelta) throws IOException {
        switch (ruolo) {
            case "user" -> handleUserChoice(scelta);
            case "nutritionist" -> handleNutritionistChoice(scelta);
            case "restaurant" -> handleRestaurantChoice(scelta);
            default -> logger.warning("Ruolo non valido.");
        }
    }

    private void handleUserChoice(String scelta) throws IOException {
        switch (scelta) {
            case "1" -> new FollowDietCLI(scanner).start();
            case "2" -> new InsertMealCLI(scanner).start();
            case "3" -> new UserReportCLI(scanner).start();
            case "4" -> logger.info("Ricerca ristorante...");
            case "5" -> new ResultsoDietCLI(scanner).start();
            case "6" -> {
                SessionManagerSLT.getInstance().logout();
                new LoginCLI(scanner).start();
            }
            default -> logger.warning("Opzione non valida.");
        }
    }

    private void handleNutritionistChoice(String scelta) {
        switch (scelta) {
            case "1" -> new AddDietCLI(scanner).start();
            case "2" -> new ViewAnsByNutritionistCLI(scanner).start();
            case "3" -> {
                SessionManagerSLT.getInstance().logout();
                new LoginCLI(scanner).start();
            }
            default -> logger.warning("scegliere un numero valido.");
        }
    }

    private void handleRestaurantChoice(String scelta) {
        switch (scelta) {
            case "1" -> logger.info("Ricerca ristorante (guest)...");
            case "2" -> {
                SessionManagerSLT.getInstance().logout();
                new LoginCLI(scanner).start();
            }
            default -> logger.warning("Scelta non valida.");
        }
    }

}
