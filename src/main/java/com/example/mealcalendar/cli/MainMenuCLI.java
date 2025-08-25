package com.example.mealcalendar.cli;

import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.dao.DietDAO;
import com.example.mealcalendar.dao.ReportRequestDAO;
import com.example.mealcalendar.dao.UserDietDAO;
import com.example.mealcalendar.exception.LoginException;
import com.example.mealcalendar.handlexceptions.DietNotFoundException;
import java.util.Scanner;


public class MainMenuCLI {

    private final Scanner scanner;

    public MainMenuCLI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() throws DietNotFoundException {

        ReportRequestDAO.getInstance().getAll();
        UserDietDAO.getInstance().getAllUsers();
        DietDAO.getInstance().getAllDiets();

        while (true) {

            String username = SessionManagerSLT.getInstance().getLoggedInUsername();
            String ruolo = SessionManagerSLT.getInstance().getLoggedRole();

            System.out.println("===== Menu Principale =====");
            if (username != null) {
                System.out.println("Ciao, " + username + "!");
            }
            if (ruolo != null) {
                System.out.println("Sei un " + ruolo);
            }

            if (!showOptions(ruolo)) {
                System.out.println("Ruolo non riconosciuto o utente non loggato.");
                return;
            }

            String scelta = scanner.nextLine().trim();
            try {
                handleChoice(ruolo, scelta);
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
    }

    private boolean showOptions(String ruolo) {
        switch (ruolo) {
            case "user" -> {
                System.out.println("1. Segui dieta");
                System.out.println("2. Inserisci pasti");
                System.out.println("3. Visualizza report");
                System.out.println("4. Cerca ristorante");
                System.out.println("5. Visualizza risultati");
                System.out.println("6. Logout");
            }
            case "nutritionist" -> {
                System.out.println("1. Crea dieta");
                System.out.println("2. Visualizza richieste");
                System.out.println("3. Logout");
            }
            case "restaurant" -> {
                System.out.println("1. Cerca ristorante (guest)");
                System.out.println("2. Logout");
            }
            default -> {
                return false;
            }
        }
        return true;
    }

    private void handleChoice(String ruolo, String scelta) throws DietNotFoundException, LoginException {
        switch (ruolo) {
            case "user" -> handleUserChoice(scelta);
            case "nutritionist" -> handleNutritionistChoice(scelta);
            case "restaurant" -> handleRestaurantChoice(scelta);
            default -> System.out.println("Ruolo non valido.");
        }
    }

    private void handleUserChoice(String scelta) throws DietNotFoundException, LoginException {
        switch (scelta) {
            case "1" -> new FollowDietCLI(scanner).start();
            case "2" -> new InsertMealCLI(scanner).start();
            case "3" -> new UserReportCLI(scanner).start();
            case "4" -> System.out.println("Ricerca ristorante...");
            case "5" -> new ResultsoDietCLI(scanner).start();
            case "6" -> {
                SessionManagerSLT.getInstance().logout();
                new LoginCLI(scanner).start();
            }
            default -> System.out.println("Opzione non valida.");
        }
    }

    private void handleNutritionistChoice(String scelta) throws DietNotFoundException {
        switch (scelta) {
            case "1" -> new AddDietCLI(scanner).start();
            case "2" -> new ViewAnsByNutritionistCLI(scanner).start();
            case "3" -> {
                SessionManagerSLT.getInstance().logout();
                new LoginCLI(scanner).start();
            }
            default -> System.out.println("scegliere un numero valido.");
        }
    }

    private void handleRestaurantChoice(String scelta) {
        switch (scelta) {
            case "1" -> System.out.println("Ricerca ristorante (guest)...");
            case "2" -> {
                SessionManagerSLT.getInstance().logout();
                new LoginCLI(scanner).start();
            }
            default -> System.out.println("Scelta non valida.");
        }
    }

}
