package com.example.mealcalendar.cli;


import com.example.mealcalendar.bean.ReportReponseBean;
import com.example.mealcalendar.controller_applicativo.RequestNutritionsReportController;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserReportCLI {

    private static final Logger logger = Logger.getLogger(UserReportCLI.class.getName());
    private final Scanner scanner;
    private final RequestNutritionsReportController controller;

    public UserReportCLI(Scanner scanner) {
        this.scanner = scanner;
        this.controller = new RequestNutritionsReportController();
    }

    public void start() {

        System.out.println("=== Visualizza il resoconto del nutrizionista ===");

        ReportReponseBean report = controller.getLatestResponseForUser();

        if (report != null) {
            showReport(report);

            System.out.println("Premi INVIO per completare e cancellare il resoconto... ");
            scanner.nextLine();

            controller.deletediet();
            System.out.println("Resoconto completato e rimosso. Torno al menu.");
        } else {
            System.out.println("Nessun resoconto disponibile per te al momento.");
        }

        new MainMenuCLI(scanner).start();
    }

        private void showReport(ReportReponseBean report)
        {
            if (logger.isLoggable(Level.INFO)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Dieta: ").append(report.getDietName()).append("\n")
                        .append("Nutrizionista: ").append(report.getNutritionistName()).append("\n")
                        .append("Risposta:\n").append(report.getResponseText()).append("\n");

                System.out.println(sb.toString());
            }
        }

}
