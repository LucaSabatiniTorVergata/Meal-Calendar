package com.example.mealcalendar;

import java.util.Scanner;

public class MainMenuViewBoundaryCli {
    private final Scanner scanner = new Scanner(System.in);
    private final CliController cliController = new CliController();
    private final AntiCodeSmellPrinter printer = new AntiCodeSmellPrinter("MainMenuViewBoundaryCli");

    public void start() throws Exception {
        printer.print("\n===== Menu Principale (CLI) =====");
        printer.print("1. Trova Ristorante (Guest)");
        printer.print("2. Trova Ristorante (Utente Registrato)");
        printer.print("3. Imposta Orario Pasto");
        printer.print("4. Trova Ricetta");
        printer.print("5. Riempi Frigorifero");
        printer.print("6. Logout");
        printer.print("Scegli un'opzione: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> findRestaurantGuest();
            case "2" -> findRestaurantUser();
            case "3" -> loadCalendarMenu();
            case "4" -> loadFindRecipe();
            case "5" -> loadFridgeMenu();
            case "6" -> logout();
            default -> {
                printer.print("❌ Scelta non valida.");
                start();
            }
        }
    }

    // Metodi per le funzionalità disponibili per il guest
    private void findRestaurantGuest() throws Exception {
        printer.print("\n🔍 Trova Ristorante (Guest)");
        cliController.navigateTo("ristoranteguest");
    }

    // Metodi per le funzionalità disponibili per l'utente registrato
    private void findRestaurantUser() throws Exception {
        printer.print("\n🔍 Trova Ristorante (Utente Registrato)");
        cliController.navigateTo("ristoranteUser");
    }

    private void loadCalendarMenu() throws Exception {
        printer.print("\n📅 Imposta Orario Pasto");
        cliController.navigateTo("calendariopasti");
    }

    private void loadFindRecipe() throws Exception {
        printer.print("\n🍲 Trova Ricetta");
        cliController.navigateTo("trovaricette");
    }

    private void loadFridgeMenu() throws Exception {
        printer.print("\n🧊 Riempi Frigorifero");
        cliController.navigateTo("fridge");
    }

    // Logout e ritorno alla schermata di login
    private void logout() throws Exception {
        SessionManagerSLT.getInstance().logout();
        printer.print("👋 Logout effettuato.");
        cliController.navigateTo("login");
    }

    // Metodo di inizializzazione per visualizzare il nome utente (se loggato)
    public void initialize() {
        String username = SessionManagerSLT.getInstance().getLoggedInUsername();
        if (username != null) {
            printer.print("\n👤 Benvenuto, " + username + "!");
        }
    }
}
