package com.example.mealcalendar.dao;

import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.model.TakenDietEntity;
import com.example.mealcalendar.model.TakenDayEntity;
import com.example.mealcalendar.model.TakenMealEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DietTakenDAO {

    private static DietTakenDAO instance=null;

    private static final String FILE_PATH = "dietTaken.json";

    private final List<TakenDietEntity> ramStorage = new ArrayList<>();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private DietTakenDAO() {

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("[]");
                System.out.println("[DietTakenDAO] File creato: " + FILE_PATH);
            } catch (IOException e) {
                throw new IllegalStateException("Errore creazione file dietTaken.json", e);
            }
        }
    }

    public static DietTakenDAO getInstance() {
        if (instance == null) {
            instance = new DietTakenDAO();
        }
        return instance;
    }

    public void insertDiet(TakenDietEntity entity) {
        switch (SessionManagerSLT.getInstance().getPersistenceType()) {
            case RAM -> {

                ramStorage.add(entity);
                printTakenDiet(entity, "[DietTakenDAO][RAM] Dieta assunta salvata:");

            }
            case FILESYSTEM -> {
                List<TakenDietEntity> all = loadFromFile();
                all.add(entity);
                try (FileWriter writer = new FileWriter(FILE_PATH)) {
                    gson.toJson(all, writer);
                    System.out.println("[DietTakenDAO][FILESYSTEM] Dieta assunta salvata su file.");
                    printTakenDiet(entity, "Dati salvati:");
                } catch (IOException e) {
                    throw new IllegalStateException("Errore nel salvataggio su file", e);
                }
                ramStorage.clear();
                ramStorage.addAll(all);
            }
            case DATABASE -> throw new UnsupportedOperationException("DB non ancora supportato");
        }
    }

    public List<TakenDietEntity> getAll() {
        switch (SessionManagerSLT.getInstance().getPersistenceType()) {
            case RAM -> {
                System.out.println("[DietTakenDAO][RAM] Recupero " + ramStorage.size() + " diete assunte.");
                return new ArrayList<>(ramStorage);
            }
            case FILESYSTEM -> {
                List<TakenDietEntity> all = loadFromFile();
                ramStorage.clear();
                ramStorage.addAll(all);
                System.out.println("[DietTakenDAO][FILESYSTEM] Caricate " + all.size() + " diete assunte.");
                return all;
            }
            case DATABASE -> throw new UnsupportedOperationException("DB non ancora supportato");
        }
        return new ArrayList<>();
    }

    public void deleteByUser(String username) {
        switch (SessionManagerSLT.getInstance().getPersistenceType()) {
            case RAM ->
                ramStorage.removeIf(d -> d.getUser().equalsIgnoreCase(username));

            case FILESYSTEM -> {
                List<TakenDietEntity> all = loadFromFile();
                all.removeIf(d -> d.getUser().equalsIgnoreCase(username));
                try (FileWriter writer = new FileWriter(FILE_PATH)) {
                    gson.toJson(all, writer);
                } catch (IOException e) {
                    throw new IllegalStateException("Errore durante la cancellazione della dieta assunta", e);
                }
                ramStorage.clear();
                ramStorage.addAll(all);
            }
            case DATABASE -> throw new UnsupportedOperationException("DB non ancora supportato");
        }
    }


    private List<TakenDietEntity> loadFromFile() {

        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<TakenDietEntity>>() {}.getType();
            List<TakenDietEntity> loaded = gson.fromJson(reader, listType);
            return loaded != null ? loaded : new ArrayList<>();
        } catch (IOException e) {
            throw new IllegalStateException("Errore caricamento file dietTaken.json", e);
        }

    }

    private void printTakenDiet(TakenDietEntity entity, String header) {

        System.out.println(header);
        System.out.println("- Utente: " + entity.getUser());
        System.out.println("- Numero giorni: " + entity.getDays().size());

        for (TakenDayEntity day : entity.getDays()) {
            System.out.println("  Giorno " + day.getGiorno() + ": " + day.getPasti().size() + " pasti");
            for (TakenMealEntity meal : day.getPasti()) {
                System.out.println("    - " + meal.getNome() + " | kcal=" + meal.getKcal() + " | descr: " + meal.getDescrizione());
            }
        }
    }

    public List<TakenDietEntity> getRamStorage(){
        return ramStorage;
    }

    public TakenDietEntity getByUser(String username) {
        for (TakenDietEntity e : getAll()) {
            if (username.equalsIgnoreCase(e.getUser())) {
                return e;
            }
        }
        return null;
    }

}
