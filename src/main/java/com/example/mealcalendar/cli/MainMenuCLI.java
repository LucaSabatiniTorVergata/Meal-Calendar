package com.example.mealcalendar.cli;

import com.example.mealcalendar.SessionManagerSLT;

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

            logger.info("Seleziona un'opzione:");
            if ("user".equals(ruolo)) {
                logger.info("1. Segui dieta");
                logger.info("2. Inserisci pasti");
                logger.info("3. Visualizza report");
                logger.info("4. Cerca ristorante");
                logger.info("5. Visualizza risultati");
                logger.info("6. Logout");
            } else if ("nutritionist".equals(ruolo)) {
                logger.info("1. Crea dieta");
                logger.info("2. Visualizza richieste");
                logger.info("3. Logout");
            } else if ("restaurant".equals(ruolo)) {
                logger.info("1. Cerca ristorante (guest)");
                logger.info("2. Logout");
            } else {
                logger.warning("Ruolo non riconosciuto o utente non loggato.");
                return;
            }

            String scelta = scanner.nextLine().trim();

            try {
                if ("user".equals(ruolo)) {
                    switch (scelta) {
                        case "1":
                            logger.info("Segue la dieta...");
                            new FollowDietCLI(scanner).start();
                            break;
                        case "2":
                            logger.info("Inserimento pasti...");
                            new InsertMealCLI(scanner).start();
                            break;
                        case "3":
                            logger.info("Visualizzo report...");
                            new UserReportCLI(scanner).start();
                            break;
                        case "4":
                            // findrestaurantUser
                            logger.info("Ricerca ristorante...");
                            // Chiamare controller FindRestaurantController
                            break;
                        case "5":
                            // seteatingtime
                            logger.info("Risultati dal sistema...");
                            new ResultsoDietCLI(scanner).start();
                            break;
                        case "6":
                            // logout
                            SessionManagerSLT.getInstance().logout();
                            logger.info("Logout effettuato.");
                            return;
                        default:
                            logger.warning("Opzione non valida.");
                    }
                } else if ("nutritionist".equals(ruolo)) {
                    switch (scelta) {
                        case "1":
                            logger.info("Creazione dieta...");
                            new AddDietCLI(scanner).start();
                            break;
                        case "2":
                            logger.info("Visualizzo richieste...");
                            new ViewAnsByNutritionistCLI(scanner).start();
                            break;
                        case "3":
                            SessionManagerSLT.getInstance().logout();
                            logger.info("Logout effettuato.");
                            return;
                        default:
                            logger.warning("Opzione non valida.");
                    }
                } else if ("restaurant".equals(ruolo)) {
                    switch (scelta) {
                        case "1":
                            // findrestaurantGuest
                            logger.info("Ricerca ristorante (guest)...");
                            // Chiamare controller FindRestaurantGuestController
                            break;
                        case "2":
                            SessionManagerSLT.getInstance().logout();
                            logger.info("Logout effettuato.");
                            return;
                        default:
                            logger.warning("Opzione non valida.");
                    }
                }
            } catch (Exception e) {
                logger.severe("Errore: " + e.getMessage());
            }
        }
    }
}
