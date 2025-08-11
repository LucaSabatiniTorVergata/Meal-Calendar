package com.example.mealcalendar.cli;

import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.bean.DayBean;
import com.example.mealcalendar.bean.DietBean;
import com.example.mealcalendar.bean.MealBean;
import com.example.mealcalendar.controller_applicativo.DietCreationController;
import java.util.Scanner;
import java.util.logging.Logger;

public class AddDietCLI {

    private static final Logger logger = Logger.getLogger(AddDietCLI.class.getName());
    private final Scanner scanner;

    public AddDietCLI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        logger.info("=== Creazione nuova dieta ===");

        System.out.print("Inserisci nome dieta: ");
        String nome = scanner.nextLine().trim();

        System.out.print("Inserisci descrizione: ");
        String descrizione = scanner.nextLine().trim();

        int durata = 0;

        while (durata != 7 && durata != 14) {
            System.out.print("Inserisci durata (7 o 14 giorni): ");
            try {
                durata = Integer.parseInt(scanner.nextLine().trim());
                if (durata != 7 && durata != 14) {
                    logger.warning("Durata non valida, inserisci 7 o 14.");
                }
            } catch (NumberFormatException e) {
                logger.warning("Input non valido, inserisci un numero.");
            }
        }

        DietBean dietBean = new DietBean();
        dietBean.setNome(nome);
        dietBean.setDescrizione(descrizione);
        dietBean.setDurata(durata);
        dietBean.setNutritionistUsername(SessionManagerSLT.getInstance().getLoggedInUsername());

        for (int i = 0; i < durata; i++) {
            logger.info("Giorno " + (i + 1));
            DayBean dayBean = new DayBean();
            dayBean.setGiorno(i + 1);

            for (int pastoIndex = 0; pastoIndex < 3; pastoIndex++) {
                System.out.println("Inserisci dati pasto " + (pastoIndex + 1) + ":");

                System.out.print("  Nome pasto: ");
                String nomePasto = scanner.nextLine().trim();

                int kcalPasto = -1;
                while (kcalPasto < 0) {
                    System.out.print("  Kcal: ");
                    try {
                        kcalPasto = Integer.parseInt(scanner.nextLine().trim());
                        if (kcalPasto < 0) {
                            logger.warning("Kcal deve essere positiva.");
                        }
                    } catch (NumberFormatException e) {
                        logger.warning("Inserisci un numero valido.");
                    }
                }

                System.out.print("  Descrizione: ");
                String descrizionePasto = scanner.nextLine().trim();

                MealBean mealBean = new MealBean();
                mealBean.setNome(nomePasto);
                mealBean.setKcal(kcalPasto);
                mealBean.setDescrizione(descrizionePasto);

                dayBean.addMeal(mealBean);
            }

            dietBean.addDay(dayBean);
        }

        DietCreationController controller = new DietCreationController();
        controller.saveDiet(dietBean);

        logger.info("Dieta salvata con successo.");
    }
}
