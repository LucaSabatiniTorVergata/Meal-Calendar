package com.example.mealcalendar.cli;

import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.bean.*;
import com.example.mealcalendar.controller_applicativo.FollowDietController;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class InsertMealCLI {

    private static final Logger logger = Logger.getLogger(InsertMealCLI.class.getName());
    private final Scanner scanner;

    public InsertMealCLI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {

        String email = SessionManagerSLT.getInstance().getLoggedmail();
        FollowDietController controller = new FollowDietController();
        DietBean currentDiet = controller.getAssignedDiet(email);

        if (currentDiet == null) {
            logger.info("Nessuna dieta assegnata. Prima devi scegliere una dieta.");
            return;
        }

        logger.info("Inserisci i pasti realmente assunti per la dieta: " + currentDiet.getNome());

        DietTakenBean dietTaken = new DietTakenBean();
        dietTaken.setUser(SessionManagerSLT.getInstance().getLoggedInUsername());

        List<DayBean> giorni = currentDiet.getGiorni();

        for (int i = 0; i < giorni.size(); i++) {
            DayBean day = giorni.get(i);
            logger.info("\nGiorno " + (i + 1) + ":");

            DayTakenBean dayTaken = new DayTakenBean();
            dayTaken.setGiorno(i + 1);

            List<MealBean> pasti = day.getPasti();

            for (int j = 0; j < pasti.size(); j++) {
                MealBean pasto = pasti.get(j);

                System.out.println("Pasto previsto: " + pasto.getNome());

                System.out.print("Nome pasto (premi invio per confermare il nome previsto): ");
                String nomeInserito = scanner.nextLine().trim();
                if (nomeInserito.isEmpty()) {
                    nomeInserito = pasto.getNome();
                }

                int kcalInserite = -1;
                while (kcalInserite < 0) {
                    System.out.print("Kcal assunte: ");
                    String kcalInput = scanner.nextLine().trim();
                    try {
                        kcalInserite = Integer.parseInt(kcalInput);
                        if (kcalInserite < 0) {
                            logger.warning("Inserisci un valore kcal positivo.");
                        }
                    } catch (NumberFormatException e) {
                        logger.warning("Valore kcal non valido, riprova.");
                    }
                }

                System.out.print("Descrizione pasto: ");
                String descInserita = scanner.nextLine().trim();

                MealTakenBean mealTaken = new MealTakenBean();
                mealTaken.setNome(nomeInserito);
                mealTaken.setKcal(kcalInserite);
                mealTaken.setDescrizione(descInserita);

                dayTaken.addMeal(mealTaken);
            }

            dietTaken.addDay(dayTaken);
        }

        controller.insertmeal(dietTaken);
        logger.info("Pasti inseriti correttamente. Puoi ora richiedere il resoconto.");
    }
}
