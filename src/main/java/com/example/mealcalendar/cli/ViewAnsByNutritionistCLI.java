package com.example.mealcalendar.cli;

import com.example.mealcalendar.bean.ReportRequestBean;
import com.example.mealcalendar.controller_applicativo.RequestNutritionsReportController;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewAnsByNutritionistCLI {

    private static final Logger logger = Logger.getLogger(ViewAnsByNutritionistCLI.class.getName());
    private final Scanner scanner;
    private final RequestNutritionsReportController controller = new RequestNutritionsReportController();

    public ViewAnsByNutritionistCLI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        logger.info("=== Visualizza richieste di resoconto ===");

        List<ReportRequestBean> requests = controller.getPendingRequestsForNutritionist();

        if (requests.isEmpty()) {
            logger.info("Non ci sono richieste pendenti.");
            return;
        }

        while (true) {
            logger.info("\nRichieste pendenti:");
            for (int i = 0; i < requests.size(); i++) {
                ReportRequestBean r = requests.get(i);
                logger.info((i + 1) + ". Dieta: " + r.getDietName() + " - Utente: " + r.getUserEmail());
            }
            logger.info((requests.size() + 1) + ". Torna indietro");

            System.out.print("Seleziona una richiesta da visualizzare o rispondere: ");
            String input = scanner.nextLine().trim();

            int scelta = -1;
            try {
                scelta = Integer.parseInt(input);
            } catch (NumberFormatException ignored) {}

            if (scelta == requests.size() + 1) {
                new MainMenuCLI(scanner).start();
            } else if (scelta >= 1 && scelta <= requests.size()) {
                ReportRequestBean selected = requests.get(scelta - 1);
                showRequestDetails(selected);
                askForResponse(selected);
                // Aggiorna la lista dopo possibile risposta
                requests = controller.getPendingRequestsForNutritionist();
                if (requests.isEmpty()) {
                    logger.info("Non ci sono più richieste pendenti.");
                    break;
                }
            } else {
                logger.warning("Scelta non valida. Riprova.");
            }
        }
    }

    private void showRequestDetails(ReportRequestBean request) {

        if (logger.isLoggable(Level.INFO)) {
            StringBuilder details = new StringBuilder();
            details.append("Utente: ").append(request.getUserEmail()).append("\n")
                    .append("Dieta: ").append(request.getDietName()).append("\n")
                    .append("Descrizione: ").append(request.getDietDescription()).append("\n")
                    .append("Data richiesta: ").append(request.getRequestDate()).append("\n\n");

            if (request.getDietTaken() != null && !request.getDietTaken().getDietTaken().isEmpty()) {
                for (int i = 0; i < request.getDietTaken().getDietTaken().size(); i++) {
                    details.append("Giorno ").append(i + 1).append(":\n");
                    request.getDietTaken().getDietTaken().get(i).getMealsTaken().forEach(meal -> {
                        details.append("  • ").append(meal.getNome())
                                .append(" (").append(meal.getKcal()).append(" kcal)\n")
                                .append("    ").append(meal.getDescrizione()).append("\n");
                    });
                    details.append("\n");
                }
            } else {
                details.append("Nessun pasto registrato.\n");
            }

            logger.info(details.toString());
        }
    }


    private void askForResponse(ReportRequestBean request) {

        System.out.println("Risposta attuale: " + (request.getResponse() != null ? request.getResponse() : "(nessuna)"));
        System.out.print("Inserisci la risposta (o lascia vuoto per saltare): ");
        String risposta = scanner.nextLine().trim();

        if (!risposta.isEmpty()) {
            request.setResponse(risposta);
            request.setAnswered(true);
            controller.updateResponse(request);
            logger.info("Risposta inviata con successo.");
        } else {
            logger.info("Risposta non modificata.");
        }
    }
}
