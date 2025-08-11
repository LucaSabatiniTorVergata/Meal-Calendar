package com.example.mealcalendar.cli;

import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.bean.DietBean;
import com.example.mealcalendar.bean.UserBean;
import com.example.mealcalendar.controller_applicativo.FollowDietController;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class FollowDietCLI {

    private static final Logger logger = Logger.getLogger(FollowDietCLI.class.getName());
    private final Scanner scanner;

    public FollowDietCLI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() throws IOException {
        FollowDietController controller = new FollowDietController();

        List<DietBean> diets = controller.convertdiet();

        if (diets.isEmpty()) {
            logger.info("Nessuna dieta disponibile.");
            return;
        }

        logger.info("=== Lista di diete disponibili ===");
        for (int i = 0; i < diets.size(); i++) {
            DietBean d = diets.get(i);
            System.out.printf("%d) %s - %s (Autore: %s)%n", i + 1, d.getNome(), d.getDescrizione(), d.getNutritionistUsername());
        }

        int scelta = -1;
        while (scelta < 1 || scelta > diets.size()) {
            System.out.print("Seleziona il numero della dieta da seguire: ");
            try {
                scelta = Integer.parseInt(scanner.nextLine().trim());
                if (scelta < 1 || scelta > diets.size()) {
                    logger.warning("Scelta non valida, riprova.");
                }
            } catch (NumberFormatException e) {
                logger.warning("Inserisci un numero valido.");
            }
        }

        DietBean selectedDiet = diets.get(scelta - 1);

        UserBean userBean = new UserBean(
                SessionManagerSLT.getInstance().getLoggedInUsername(),
                SessionManagerSLT.getInstance().getLoggedmail(),
                SessionManagerSLT.getInstance().getLoggedRole()
        );

        FollowDietController followController = new FollowDietController(selectedDiet, userBean);

        followController.assignDiet();

        logger.info("Dieta \"" + selectedDiet.getNome() + "\" assegnata con successo all'utente " + userBean.getNome());
    }
}
