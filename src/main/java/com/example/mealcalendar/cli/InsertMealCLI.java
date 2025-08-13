package com.example.mealcalendar.cli;

import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.bean.*;
import com.example.mealcalendar.controller_applicativo.FollowDietController;

import java.util.List;
import java.util.Scanner;


public class InsertMealCLI {


    private final Scanner scanner;

    public InsertMealCLI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        String email = SessionManagerSLT.getInstance().getLoggedmail();
        FollowDietController controller = new FollowDietController();
        DietBean currentDiet = controller.getAssignedDiet(email);

        if (currentDiet == null) {
            System.out.println("Nessuna dieta assegnata. Prima devi scegliere una dieta.");
            return;
        }

        System.out.println("Inserisci i pasti realmente assunti per la dieta: " + currentDiet.getNome());

        DietTakenBean dietTaken = new DietTakenBean();
        dietTaken.setUser(SessionManagerSLT.getInstance().getLoggedInUsername());

        List<DayBean> giorni = currentDiet.getGiorni();
        for (int i = 0; i < giorni.size(); i++) {
            DayTakenBean dayTaken = inserisciPastiGiorno(giorni.get(i), i + 1);
            dietTaken.addDay(dayTaken);
        }

        controller.insertmeal(dietTaken);
        System.out.println("Pasti inseriti correttamente. Puoi ora richiedere il resoconto.");
    }

    private DayTakenBean inserisciPastiGiorno(DayBean day, int giornoNumero) {

        System.out.println("\nGiorno " + giornoNumero + ":");

        DayTakenBean dayTaken = new DayTakenBean();
        dayTaken.setGiorno(giornoNumero);

        for (MealBean pasto : day.getPasti()) {
            MealTakenBean mealTaken = inserisciDatiPasto(pasto);
            dayTaken.addMeal(mealTaken);
        }

        return dayTaken;
    }

    private MealTakenBean inserisciDatiPasto(MealBean pasto) {

        System.out.println("Pasto previsto: " + pasto.getNome());

        System.out.print("Nome pasto (premi invio per confermare il nome previsto): ");
        String nomeInserito = scanner.nextLine().trim();
        if (nomeInserito.isEmpty()) {
            nomeInserito = pasto.getNome();
        }

        int kcalInserite = leggiKcal();

        System.out.print("Descrizione pasto: ");
        String descInserita = scanner.nextLine().trim();

        MealTakenBean mealTaken = new MealTakenBean();
        mealTaken.setNome(nomeInserito);
        mealTaken.setKcal(kcalInserite);
        mealTaken.setDescrizione(descInserita);

        return mealTaken;
    }

    private int leggiKcal() {

        int kcalInserite = -1;
        while (kcalInserite < 0) {
            System.out.print("Kcal assunte: ");
            String kcalInput = scanner.nextLine().trim();
            try {
                kcalInserite = Integer.parseInt(kcalInput);
                if (kcalInserite < 0) {
                    System.out.println("Inserisci un valore kcal positivo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Valore kcal non valido, riprova.");
            }
        }
        return kcalInserite;
    }
}
