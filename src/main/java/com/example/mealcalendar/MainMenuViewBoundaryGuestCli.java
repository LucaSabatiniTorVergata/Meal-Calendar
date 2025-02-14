package com.example.mealcalendar;

import java.util.Scanner;

public class MainMenuViewBoundaryGuestCli {
    private final Scanner scanner = new Scanner(System.in);
    private final CliController cliController = new CliController();

    public void start() throws Exception {
        System.out.println("===== Menu Ristorante Guest (CLI) =====");
        System.out.println("1. Trova Ristorante (Guest)");
        System.out.println("2. Torna alla Home");
        System.out.print("Scegli un'opzione: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                cliController.navigateTo("ristoranteguest");
                break;
            case "2":
                cliController.navigateTo("login");
                break;
            default:
                System.out.println("❌ Scelta non valida.");
                start();
        }
    }
}

