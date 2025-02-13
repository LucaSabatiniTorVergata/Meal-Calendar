package com.example.mealcalendar;

import java.io.IOException;
import java.util.Scanner;

public class MainMenuViewBoundaryCli {
    private final Scanner scanner = new Scanner(System.in);
    private final CliController cliController = new CliController();
    private boolean usePersistence = false;

    public void start() {
        System.out.println("===== Menu Principale (CLI) =====");
        System.out.println("1. Trova Ristorante (Guest)");
        System.out.println("2. Trova Ristorante (Utente Registrato)");
        System.out.println("3. Imposta Orario Pasto");
        System.out.println("4. Trova Ricetta");
        System.out.println("5. Riempi Frigorifero");
        System.out.println("6. Abilita Persistenza");
        System.out.println("7. Disabilita Persistenza");
        System.out.println("8. Torna alla Home");
        System.out.println("9. Logout");
        System.out.print("Scegli un'opzione: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                cliController.navigateTo("ristoranteguest");
                break;
            case "2":
                cliController.navigateTo("ristoranteUser");
                break;
            case "3":
                loadCalendarMenu();
                break;
            case "4":
                cliController.navigateTo("trovaricette");
                break;
            case "5":
                loadFridgeMenu();
                break;
            case "6":
                enablePersistence();
                break;
            case "7":
                disablePersistence();
                break;
            case "8":
                cliController.navigateTo("home");
                break;
            case "9":
                cliController.navigateTo("login");
                break;
            default:
                System.out.println("❌ Scelta non valida.");
                start();
        }
    }

    private void findRestaurantGuest() {
        System.out.println("Vai a: Trova Ristorante (Guest)");
    }

    private void findRestaurantUser() {
        System.out.println("Vai a: Trova Ristorante (Utente Registrato)");
    }

    private void loadCalendarMenu() {
        System.out.println("Vai a: Imposta Orario Pasto");
    }

    private void loadFindRecipe() {
        System.out.println("Vai a: Trova Ricetta");
    }

    private void loadFridgeMenu() {
        System.out.println("Vai a: Riempi Frigorifero");
    }

    private void enablePersistence() {
        usePersistence = true;
        System.out.println("✅ Persistenza abilitata.");
        start();
    }

    private void disablePersistence() {
        usePersistence = false;
        System.out.println("✅ Persistenza disabilitata.");
        start();
    }
}
