package com.example.mealcalendar.dao;


import com.example.mealcalendar.model.DayEntity;
import com.example.mealcalendar.model.DietEntity;
import com.example.mealcalendar.model.MealEntity;
import com.example.mealcalendar.model.UserEntity;

import java.util.*;

public class RamUserDietDAO implements UserDietDAO{

    private final Map<String, UserEntity> cache = new HashMap<>();

    private static RamUserDietDAO instance=null;

    private RamUserDietDAO() {}

    public static RamUserDietDAO getInstance() {
        if (instance == null) {
            instance = new RamUserDietDAO();
        }
        return instance;
    }

    @Override
    public void saveUser(UserEntity user) {

        cache.put(user.getEmail(), user);
        printAllUsersWithDiet();

    }

    @Override
    public UserEntity getUserByEmail(String email) {

        return cache.get(email);

    }

    @Override
    public List<UserEntity> getAllUsers() {

        return new ArrayList<>(cache.values());

    }

    public void clearCache() {

        cache.clear();

    }

    public void printAllUsersWithDiet() {

        if (cache.isEmpty()) {
            System.out.println("🟡 Nessun utente presente nella cache.");
            return;
        }

        System.out.println("🟢 Utenti presenti nella cache:");
        for (UserEntity user : cache.values()) {
            System.out.println("👤 Nome: " + user.getNome() +
                    ", Email: " + user.getEmail() +
                    ", Ruolo: " + user.getRuolo());

            DietEntity dieta = user.getDietaAssegnata();
            if (dieta == null) {

                System.out.println("   🔸 Nessuna dieta assegnata.");

            } else {

                System.out.println("   🥗 Dieta: " + dieta.getNome() +
                        " | Descrizione: " + dieta.getDescrizione() +
                        " | Durata: " + dieta.getDurata() + " giorni");

                for (DayEntity day : dieta.getGiorni()) {
                    System.out.println("      📅 Giorno " + day.getGiorno());
                    for (MealEntity meal : day.getPasti()) {
                        System.out.println("         🍽️ " + meal.getNome() +
                                " | " + meal.getKcal() + " kcal" +
                                " | " + meal.getDescrizione());
                    }
                }
            }
            System.out.println(); // Riga vuota tra utenti
        }
    }

}

