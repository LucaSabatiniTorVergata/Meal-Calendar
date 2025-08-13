package com.example.mealcalendar.cli;

import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.bean.DayBean;
import com.example.mealcalendar.bean.DietBean;
import com.example.mealcalendar.bean.MealBean;
import com.example.mealcalendar.controller_applicativo.DietCreationController;
import java.util.Scanner;


public class AddDietCLI {


    private final Scanner scanner;

    public AddDietCLI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        System.out.println("=== Creazione nuova dieta ===");

        DietBean dietBean = leggiDatiDieta();

        for (int i = 0; i < dietBean.getDurata(); i++) {
            DayBean dayBean = leggiGiorno(i + 1);
            dietBean.addDay(dayBean);
        }

        new DietCreationController().saveDiet(dietBean);
        System.out.println("Dieta salvata con successo.");
    }

    private DietBean leggiDatiDieta() {
        System.out.print("Inserisci nome dieta: ");
        String nome = scanner.nextLine().trim();

        System.out.print("Inserisci descrizione: ");
        String descrizione = scanner.nextLine().trim();

        int durata = leggiDurata();

        DietBean dietBean = new DietBean();
        dietBean.setNome(nome);
        dietBean.setDescrizione(descrizione);
        dietBean.setDurata(durata);
        dietBean.setNutritionistUsername(SessionManagerSLT.getInstance().getLoggedInUsername());
        return dietBean;
    }

    private int leggiDurata() {
        int durata = 0;
        while (durata != 7 && durata != 14) {
            System.out.print("Inserisci durata (7 o 14 giorni): ");
            try {
                durata = Integer.parseInt(scanner.nextLine().trim());
                if (durata != 7 && durata != 14) {
                    System.out.println("Durata non valida, inserisci 7 o 14.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input non valido, inserisci un numero.");
            }
        }
        return durata;
    }

    private DayBean leggiGiorno(int numeroGiorno) {

        System.out.println("Giorno " + numeroGiorno);
        DayBean dayBean = new DayBean();
        dayBean.setGiorno(numeroGiorno);

        for (int i = 0; i < 3; i++) {
            MealBean meal = leggiPasto(i + 1);
            dayBean.addMeal(meal);
        }
        return dayBean;
    }

    private MealBean leggiPasto(int indicePasto) {

        System.out.println("Inserisci dati pasto " + indicePasto + ":");

        System.out.print("  Nome pasto: ");
        String nomePasto = scanner.nextLine().trim();

        int kcalPasto = leggiKcal();

        System.out.print("  Descrizione: ");
        String descrizionePasto = scanner.nextLine().trim();

        MealBean mealBean = new MealBean();
        mealBean.setNome(nomePasto);
        mealBean.setKcal(kcalPasto);
        mealBean.setDescrizione(descrizionePasto);
        return mealBean;
    }

    private int leggiKcal() {
        int kcalPasto = -1;
        while (kcalPasto < 0) {
            System.out.print("  Kcal: ");
            try {
                kcalPasto = Integer.parseInt(scanner.nextLine().trim());
                if (kcalPasto < 0) {
                    System.out.println("Kcal deve essere positiva.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido.");
            }
        }
        return kcalPasto;
    }

}
