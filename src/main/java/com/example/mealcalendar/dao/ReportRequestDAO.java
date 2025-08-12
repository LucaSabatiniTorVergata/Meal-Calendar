package com.example.mealcalendar.dao;

import com.example.mealcalendar.LocalDateAdapter;
import com.example.mealcalendar.LocalDateTimeAdapter;
import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.model.ReportRequestEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.sql.Connection;
import java.io.*;
import java.lang.reflect.Type;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReportRequestDAO {

    private static ReportRequestDAO instance = null;

    private final List<ReportRequestEntity> ramStorage = new ArrayList<>();

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .setPrettyPrinting()
            .create();

    private static final String FILE_PATH = "reportRequests.json";

    private ReportRequestDAO() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("[]");
                System.out.println("[ReportRequestDAO] File creato: " + FILE_PATH);
            } catch (IOException e) {
                throw new IllegalStateException("Errore creazione file reportRequests.json", e);
            }
        }
    }

    public static synchronized ReportRequestDAO getInstance() {
        if (instance == null) {
            instance = new ReportRequestDAO();
        }
        return instance;
    }

    public void save(ReportRequestEntity request) {
        switch (SessionManagerSLT.getInstance().getPersistenceType()) {

            case RAM -> {
                ramStorage.add(request);
                System.out.println("[ReportRequestDAO][RAM] Report request salvato per utente: " + request.getUserEmail());
            }
            case FILESYSTEM -> {
                List<ReportRequestEntity> all = loadFromFile();
                all.add(request);
                try (FileWriter writer = new FileWriter(FILE_PATH)) {
                    gson.toJson(all, writer);
                    System.out.println("[ReportRequestDAO][FILESYSTEM] Report request salvato su file.");
                } catch (IOException e) {
                    throw new IllegalStateException("Errore nel salvataggio report request", e);
                }
                ramStorage.clear();
                ramStorage.addAll(all);
            }

            case DATABASE -> throw new UnsupportedOperationException("DB non ancora supportato");

        }
    }

    public void update(ReportRequestEntity request) {

        switch (SessionManagerSLT.getInstance().getPersistenceType()) {
            case RAM -> {
                int idx = findIndexByRequest(request);
                if (idx != -1) {
                    ramStorage.set(idx, request);

                }
            }
            case FILESYSTEM -> {
                List<ReportRequestEntity> all = loadFromFile();
                int idx = -1;
                for (int i = 0; i < all.size(); i++) {
                    if (equalsRequest(all.get(i), request)) {
                        idx = i;
                        break;
                    }
                }
                if (idx != -1) {
                    all.set(idx, request);
                    try (FileWriter writer = new FileWriter(FILE_PATH)) {
                        gson.toJson(all, writer);
                        System.out.println("[ReportRequestDAO][FILESYSTEM] Report request aggiornato su file.");
                    } catch (IOException e) {
                        throw new IllegalStateException("Errore aggiornamento report request", e);
                    }
                    ramStorage.clear();
                    ramStorage.addAll(all);
                }
            }
            case DATABASE -> throw new UnsupportedOperationException("DB non ancora supportato");

        }
    }

    public List<ReportRequestEntity> getAll() {
        switch (SessionManagerSLT.getInstance().getPersistenceType()) {

            case RAM -> {
                return new ArrayList<>(ramStorage);
            }

            case FILESYSTEM -> {
                List<ReportRequestEntity> loaded = loadFromFile();
                ramStorage.clear();
                ramStorage.addAll(loaded);
                return loaded;
            }

            case DATABASE -> throw new UnsupportedOperationException("DB non ancora supportato");
        }

        return Collections.emptyList();
    }


    public void deleteByUser(String username) {
        switch (SessionManagerSLT.getInstance().getPersistenceType()) {

            case RAM -> {
                ramStorage.removeIf(r -> r.getDietTaken().getUser().equalsIgnoreCase(username));
                System.out.println("[ReportRequestDAO][RAM] Richiesta rimossa per utente: " + username);
            }

            case FILESYSTEM -> {
                List<ReportRequestEntity> all = loadFromFile();
                boolean removed = all.removeIf(r -> r.getDietTaken().getUser().equalsIgnoreCase(username));
                if (removed) {
                    try (FileWriter writer = new FileWriter(FILE_PATH)) {
                        gson.toJson(all, writer);
                        System.out.println("[ReportRequestDAO][FILESYSTEM] Richiesta rimossa su file per utente: " + username);
                    } catch (IOException e) {
                        throw new IllegalStateException("Errore nella rimozione report request", e);
                    }
                    ramStorage.clear();
                    ramStorage.addAll(all);
                }
            }

            case DATABASE -> throw new UnsupportedOperationException("DB non ancora supportato");

        }
    }


    public List<ReportRequestEntity> getAllByNutritionist(String nutritionistEmail) {
        List<ReportRequestEntity> all = getAll();
        List<ReportRequestEntity> filtered = new ArrayList<>();
        for (ReportRequestEntity r : all) {
            if (r.getNutritionistEmail().equalsIgnoreCase(nutritionistEmail)) {
                filtered.add(r);
            }
        }
        return filtered;
    }

    private List<ReportRequestEntity> loadFromFile() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<ReportRequestEntity>>(){}.getType();
            List<ReportRequestEntity> loaded = gson.fromJson(reader, listType);
            return loaded != null ? loaded : new ArrayList<>();
        } catch (IOException e) {
            throw new IllegalStateException("Errore caricamento file reportRequests.json", e);
        }
    }

    private int findIndexByRequest(ReportRequestEntity req) {
        for (int i = 0; i < ramStorage.size(); i++) {
            if (equalsRequest(ramStorage.get(i), req)) return i;
        }
        return -1;
    }

    private boolean equalsRequest(ReportRequestEntity r1, ReportRequestEntity r2) {
        return r1.getUserEmail().equalsIgnoreCase(r2.getUserEmail())
                && r1.getNutritionistEmail().equalsIgnoreCase(r2.getNutritionistEmail())
                && r1.getRequestTime().equals(r2.getRequestTime());
    }

    public List<ReportRequestEntity> getRamStorage() {
        return ramStorage;
    }
}
