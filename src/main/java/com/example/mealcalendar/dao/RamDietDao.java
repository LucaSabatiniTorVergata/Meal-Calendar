package com.example.mealcalendar.dao;

import com.example.mealcalendar.bean.DayBean;
import com.example.mealcalendar.bean.DietBean;
import com.example.mealcalendar.bean.MealBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RamDietDao implements DietDAO {

    private static RamDietDao instance;

    private final Map<String, List<DietBean>> data = new HashMap<>();

    private RamDietDao() {}

    public static synchronized RamDietDao getInstance() {
        if (instance == null) {
            instance = new RamDietDao();
        }
        return instance;
    }


    @Override
    public void saveDiet(String nutritionistUsername, DietBean diet) {
        data.putIfAbsent(nutritionistUsername, new ArrayList<>());
        data.get(nutritionistUsername).add(diet);
        printAllDiets();
    }

    @Override
    public List<DietBean> getDietsByNutritionist(String nutritionistUsername) {
        return data.getOrDefault(nutritionistUsername, new ArrayList<>());
    }

    @Override
    public List<DietBean> getAllDiets() {
        List<DietBean> allDiets = new ArrayList<>();
        for (List<DietBean> diets : data.values()) {
            allDiets.addAll(diets);
        }
        return allDiets;
    }

    public void printAllDiets() {
        for (Map.Entry<String, List<DietBean>> entry : data.entrySet()) {
            String nutritionist = entry.getKey();
            System.out.println("👨‍⚕️ Diete per: " + nutritionist);
            for (DietBean diet : entry.getValue()) {
                System.out.println("📋 Dieta: " + diet.getNome());
                System.out.println("📝 Descrizione: " + diet.getDescrizione());
                System.out.println("📆 Durata: " + diet.getDurata() + " giorni");

                int giornoCount = 1;
                for (DayBean giorno : diet.getGiorni()) {
                    System.out.println("  📅 Giorno " + giornoCount++ + ":");
                    for (MealBean pasto : giorno.getPasti()) {
                        System.out.println("    🍽️ Pasto: " + pasto.getNome());
                        System.out.println("       Descrizione: " + pasto.getDescrizione());
                        System.out.println("       Kcal: " + pasto.getKcal());
                    }
                }
                System.out.println("------------------------------------");
            }
        }
    }

    public void clearCache() {
        data.clear();
    }



}
