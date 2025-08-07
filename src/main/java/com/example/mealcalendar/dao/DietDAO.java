package com.example.mealcalendar.dao;

import com.example.mealcalendar.bean.DietBean;
import com.example.mealcalendar.model.DietEntity;
import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.PersistenceType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class DietDAO {

    private static DietDAO instance=null;

    // RAM condivisa (vale anche come cache)
    private final  List<DietEntity> ramStorage = new ArrayList();

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static final String FILE_PATH = "diets.json";

    public DietDAO() {

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("{}");
            } catch (IOException e) {
                throw new IllegalArgumentException("Impossibile creare il file JSON", e);
            }
        }
    }

    public static synchronized DietDAO getInstance() {
        if (instance == null) {
            instance = new DietDAO();
        }
        return instance;
    }


    public void saveDiet( DietEntity diet) {
        switch (SessionManagerSLT.getInstance().getPersistenceType()) {

            case RAM -> {
                ramStorage.add(diet);
                printDietDetails("[DietDAO][RAM] Dieta salvata in RAM:", diet);
            }

            case FILESYSTEM ->{

                List<DietEntity> allDiets = loadAllDietsList();
                allDiets.add(diet);
                try (FileWriter writer = new FileWriter(FILE_PATH)) {
                    gson.toJson(allDiets, writer);
                } catch (IOException e) {
                    throw new IllegalArgumentException("Errore nel salvataggio della dieta", e);
                }
                printDietDetails("Dettagli dieta salvata:", diet);

            }


            case DATABASE -> throw new UnsupportedOperationException("DB non ancora supportato");

        }
    }


    public List<DietEntity> getAllDiets() {
        switch (SessionManagerSLT.getInstance().getPersistenceType()) {

            case RAM -> {
                return new ArrayList<>(ramStorage);
            }


            case FILESYSTEM -> {

                List<DietEntity> loaded = loadAllDietsList();

                // Pulisco e aggiorno la cache RAM
                ramStorage.clear();
                ramStorage.addAll(loaded);

                return loaded;

            }

            case DATABASE -> throw new UnsupportedOperationException("DB non ancora supportato");
        }
        return Collections.emptyList();
    }


    private List<DietEntity> loadAllDietsList() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<DietEntity>>() {}.getType();
            List<DietEntity> loaded = gson.fromJson(reader, listType);
            return loaded != null ? loaded : new ArrayList<>();
        } catch (IOException e) {
            throw new IllegalArgumentException("Errore nella lettura delle diete", e);
        }
    }

    public List<DietEntity> getRamStorage() {
        return ramStorage;
    }

    private void printDietDetails(String header, DietEntity diet) {
        System.out.println(header);
        System.out.println("- Nome: " + diet.getNome());
        System.out.println("- Durata: " + diet.getDurata());
        System.out.println("- Descrizione: " + diet.getDescrizione());
        System.out.println("- Nutritionist: " + diet.getNutritionistUsername());
        System.out.println("- Giorni: " + diet.getGiorni().size());
        int i = 1;
        for (var day : diet.getGiorni()) {
            System.out.println("  Giorno " + i++ );
            int j = 1;
            for (var meal : day.getPasti()) {
                System.out.println("    Pasto " + j++ + ": " + meal.getNome() + ", kcal=" + meal.getKcal() + meal.getDescrizione());
            }
        }
    }

}
