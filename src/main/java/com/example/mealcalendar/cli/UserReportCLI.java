package com.example.mealcalendar.cli;


import com.example.mealcalendar.bean.ReportReponseBean;
import com.example.mealcalendar.controller_applicativo.RequestNutritionsReportController;

import java.util.Scanner;
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
        logger.info("=== Visualizza il resoconto del nutrizionista ===");

        ReportReponseBean report = controller.getLatestResponseForUser();

        if (report == null) {
            logger.info("Nessun resoconto disponibile per te al momento.");
            return;
        }

        showReport(report);

        logger.info("Premi INVIO per completare e cancellare il resoconto... ");
        scanner.nextLine();

        controller.deletediet();
        logger.info("Resoconto completato e rimosso. Torno al menu.");
        new MainMenuCLI(scanner).start();
    }

    private void showReport(ReportReponseBean report) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dieta: ").append(report.getDietName()).append("\n")
                .append("Nutrizionista: ").append(report.getNutritionistName()).append("\n")
                .append("Risposta:\n").append(report.getResponseText()).append("\n");

        logger.info(sb.toString());
    }
}
