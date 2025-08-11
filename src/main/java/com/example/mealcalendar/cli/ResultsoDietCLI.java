package com.example.mealcalendar.cli;

import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.controller_applicativo.FollowDietController;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class ResultsoDietCLI {

    private static final Logger logger = Logger.getLogger(ResultsoDietCLI.class.getName());
    private final Scanner scanner;

    public ResultsoDietCLI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        FollowDietController controller = new FollowDietController();
        List<String> resoconto = controller.generaResoconto();

        logger.info("\n--- Resoconto Dieta ---");
        for (String giorno : resoconto) {
            System.out.println("\n" + giorno);
        }

        while (true) {
            System.out.println("\nAzioni disponibili:");
            System.out.println("1. Torna al menu principale");
            System.out.println("2. Termina dieta");
            System.out.println("3. Richiedi resoconto al nutrizionista");
            System.out.print("Scegli un'opzione (1-3): ");

            String scelta = scanner.nextLine().trim();

            switch (scelta) {
                case "1":
                    logger.info("Tornando al menu principale...");
                    new MainMenuCLI(scanner).start();
                    return;

                case "2":
                    controller.delete(SessionManagerSLT.getInstance().getLoggedInUsername(),
                            SessionManagerSLT.getInstance().getLoggedmail());
                    logger.info("Dieta terminata e rimossa.");
                    return;

                case "3":
                    SessionManagerSLT.getInstance().setRequestnutr(true);
                    boolean richiestaInviata = controller.requnutr();
                    if (richiestaInviata) {
                        logger.info("Richiesta inviata al nutrizionista.");
                    } else {
                        logger.warning("Errore nell'invio della richiesta.");
                    }
                    return;

                default:
                    logger.warning("Opzione non valida, riprova.");
            }
        }
    }
}
