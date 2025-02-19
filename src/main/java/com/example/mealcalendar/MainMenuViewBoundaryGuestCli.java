package com.example.mealcalendar;

import java.util.Scanner;

public class MainMenuViewBoundaryGuestCli {
    private final Scanner scanner = new Scanner(System.in);
    private final CliController cliController = new CliController();
    private final AntiCodeSmellPrinter printer = new AntiCodeSmellPrinter("MainMenuViewBoundaryGuestCli");

    public void start() throws Exception {
        printer.print("===== Menu Ristorante Guest (CLI) =====");
        printer.print("1. Trova Ristorante (Guest)");
        printer.print("2. Torna alla Home");
        printer.print("Scegli un'opzione: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                cliController.navigateTo("ristoranteguest");
                break;
            case "2":
                cliController.navigateTo("login");
                break;
            default:
                printer.print("❌ Scelta non valida.");
                start();
        }
    }
}
