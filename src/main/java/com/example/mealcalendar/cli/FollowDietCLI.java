package com.example.mealcalendar.cli;

import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.bean.DietBean;
import com.example.mealcalendar.bean.UserBean;
import com.example.mealcalendar.controller_applicativo.FollowDietController;
import com.example.mealcalendar.exception.LoginException;


import java.util.List;
import java.util.Scanner;


public class FollowDietCLI {


    private final Scanner scanner;

    public FollowDietCLI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() throws LoginException {

        FollowDietController controller = new FollowDietController();

        List<DietBean> diets = controller.convertdiet();

        if (diets.isEmpty()) {

            System.out.println("Nessuna dieta disponibile.");
            return;
        }

        System.out.println("=== Lista di diete disponibili ===");
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
                    System.out.println("Scelta non valida, riprova.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido.");
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

        System.out.println("Dieta \"" + selectedDiet.getNome() + "\" assegnata con successo all'utente " + userBean.getNome());
    }
}
