package com.example.mealcalendar;

import java.util.Scanner;

public class MainMenuViewBoundaryCli {
    private final Scanner scanner = new Scanner(System.in);
    private final CliController cliController = new CliController();

    public void start() throws Exception {
        System.out.println("\n===== Menu Principale (CLI) =====");
        System.out.println("1. Trova Ristorante (Guest)");
        System.out.println("2. Trova Ristorante (Utente Registrato)");
        System.out.println("3. Imposta Orario Pasto");
        System.out.println("4. Trova Ricetta");
        System.out.println("5. Riempi Frigorifero");
        System.out.println("6. Logout");
        System.out.print("Scegli un'opzione: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> findRestaurantGuest();
            case "2" -> findRestaurantUser();
            case "3" -> loadCalendarMenu();
            case "4" -> loadFindRecipe();
            case "5" -> loadFridgeMenu();
            case "6" -> logout();
            default -> {
                System.out.println("❌ Scelta non valida.");
                start();
            }
        }
    }

    // Metodi per le funzionalità disponibili per il guest
    private void findRestaurantGuest() throws Exception {
        System.out.println("\n🔍 Trova Ristorante (Guest)");
        cliController.navigateTo("ristoranteguest");
    }

    // Metodi per le funzionalità disponibili per l'utente registrato
    private void findRestaurantUser() throws Exception {
        System.out.println("\n🔍 Trova Ristorante (Utente Registrato)");
        cliController.navigateTo("ristoranteUser");
    }

    private void loadCalendarMenu() throws Exception {
        System.out.println("\n📅 Imposta Orario Pasto");
        cliController.navigateTo("calendariopasti");
    }

    private void loadFindRecipe() throws Exception {
        System.out.println("\n🍲 Trova Ricetta");
        cliController.navigateTo("trovaricette");
    }

    private void loadFridgeMenu() throws Exception {
        System.out.println("\n🧊 Riempi Frigorifero");
        cliController.navigateTo("fridge");
    }

    // Logout e ritorno alla schermata di login
    private void logout() throws Exception {
        SessionManagerSLT.getInstance().logout();
        System.out.println("👋 Logout effettuato.");
        cliController.navigateTo("login");
    }

    // Metodo di inizializzazione per visualizzare il nome utente (se loggato)
    public void initialize() {
        String username = SessionManagerSLT.getInstance().getLoggedInUsername();
        if (username != null) {
            System.out.println("\n👤 Benvenuto, " + username + "!");
        }
    }
}
