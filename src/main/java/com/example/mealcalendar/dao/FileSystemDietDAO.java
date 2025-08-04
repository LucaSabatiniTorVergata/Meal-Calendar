package com.example.mealcalendar.dao;

import com.example.mealcalendar.bean.DietBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileSystemDietDAO implements DietDAO {

    private static final String FILE_PATH = "diets.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public FileSystemDietDAO() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("{}");  // file vuoto con struttura JSON base (mappa vuota)
            } catch (IOException e) {
                throw new RuntimeException("Impossibile creare il file JSON", e);
            }
        }
    }

    @Override
    public void saveDiet(String nutritionistUsername, DietBean diet) {
        // Assicuriamoci che diet sappia chi è il nutrizionista proprietario
        diet.setNutritionistUsername(nutritionistUsername);

        Map<String, List<DietBean>> allDiets = loadAllDietsMap();

        // Se la lista non esiste, la crea
        allDiets.computeIfAbsent(nutritionistUsername, k -> new ArrayList<>()).add(diet);

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(allDiets, writer);
        } catch (IOException e) {
            throw new RuntimeException("Errore nel salvataggio della dieta", e);
        }
    }

    @Override
    public List<DietBean> getDietsByNutritionist(String nutritionistUsername) {
        Map<String, List<DietBean>> allDiets = loadAllDietsMap();
        return allDiets.getOrDefault(nutritionistUsername, new ArrayList<>());
    }

    @Override
    public List<DietBean> getAllDiets() {
        Map<String, List<DietBean>> allDiets = loadAllDietsMap();
        List<DietBean> result = new ArrayList<>();
        for (List<DietBean> list : allDiets.values()) {
            result.addAll(list);
        }
        return result;
    }

    private Map<String, List<DietBean>> loadAllDietsMap() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type type = new TypeToken<Map<String, List<DietBean>>>() {}.getType();
            Map<String, List<DietBean>> loaded = gson.fromJson(reader, type);
            return loaded != null ? loaded : new HashMap<>();
        } catch (IOException e) {
            throw new RuntimeException("Errore nella lettura delle diete", e);
        }
    }
}
