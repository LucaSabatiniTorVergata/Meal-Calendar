package com.example.mealcalendar;

import com.example.mealcalendar.cli.HelloViewCLI;
import java.util.Scanner;
import static javafx.application.Application.launch;

public class Main {

    private static final AntiCodeSmellPrinter printer = new AntiCodeSmellPrinter("Main");

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);

        printer.print("===== Calendario dei Pasti =====");
        printer.print("1. Avvia interfaccia grafica (JavaFX)");
        printer.print("2. Avvia interfaccia a linea di comando (CLI)");
        printer.print("Scegli un'opzione: ");

        String scelta = scanner.nextLine().trim();

        if (scelta.equals("1")) {
            launch();
        } else {
            HelloViewCLI cli = new HelloViewCLI(scanner);
            cli.start();
        }
    }

}
