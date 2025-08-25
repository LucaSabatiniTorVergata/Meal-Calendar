package com.example.mealcalendar.dao;


import com.example.mealcalendar.model.DayEntity;
import com.example.mealcalendar.model.DietEntity;
import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.model.MealEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.example.mealcalendar.handlexceptions.DietPersistenceException;
import com.example.mealcalendar.handlexceptions.DietNotFoundException;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

import java.sql.*;

public class DietDAO {

    private static DietDAO instance=null;

    // RAM condivisa (vale anche come cache)
    private final  List <DietEntity> ramStorage = new ArrayList<>();

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


    public void saveDiet( DietEntity diet)  throws DietPersistenceException {
        switch (SessionManagerSLT.getInstance().getPersistenceType()) {

            case RAM ->
                ramStorage.add(diet);

            case FILESYSTEM ->{

                List<DietEntity> allDiets = loadAllDietsList();
                allDiets.add(diet);
                try (FileWriter writer = new FileWriter(FILE_PATH)) {
                    gson.toJson(allDiets, writer);
                } catch (IOException e) {
                    throw new DietPersistenceException("Errore nel salvataggio della dieta su FileSystem", e);
                }


            }

            case DATABASE -> {
                String url = "jdbc:mysql://localhost:3306/mio_database";
                String user = System.getenv("DB_USER");
                String password = System.getenv("DB_PASSWORD");

                String insertDietSQL = "INSERT INTO diets (nome, descrizione, durata, nutritionist_username) VALUES (?, ?, ?, ?)";
                String insertDaySQL = "INSERT INTO diet_days (diet_id, giorno) VALUES (?, ?)";
                String insertMealSQL = "INSERT INTO meals (day_id, nome, descrizione, kcal) VALUES (?, ?, ?, ?)";

                try (Connection conn = DriverManager.getConnection(url, user, password)) {
                    conn.setAutoCommit(false);

                    try (PreparedStatement psDiet = conn.prepareStatement(insertDietSQL, Statement.RETURN_GENERATED_KEYS)) {
                        psDiet.setString(1, diet.getNome());
                        psDiet.setString(2, diet.getDescrizione());
                        psDiet.setInt(3, diet.getDurata());
                        psDiet.setString(4, diet.getNutritionistUsername());

                        psDiet.executeUpdate();

                        ResultSet generatedKeys = psDiet.getGeneratedKeys();
                        if (!generatedKeys.next()) {
                            throw new SQLException("Creazione dieta fallita, nessun ID generato.");
                        }
                        int dietId = generatedKeys.getInt(1);

                        try (PreparedStatement psDay = conn.prepareStatement(insertDaySQL, Statement.RETURN_GENERATED_KEYS);
                             PreparedStatement psMeal = conn.prepareStatement(insertMealSQL)) {

                            psMeal.clearBatch();
                            psDay.setInt(1, dietId);

                            for (DayEntity day : diet.getGiorni()) {



                                psDay.setInt(2, day.getGiorno());
                                psDay.addBatch();

                                // Eseguo subito il batch di giorno per ottenere la chiave generata
                                psDay.executeBatch();

                                try (ResultSet dayKeys = psDay.getGeneratedKeys()) {
                                    if (!dayKeys.next()) {
                                        throw new SQLException("Creazione giorno fallita, nessun ID generato.");
                                    }
                                    int dayId = dayKeys.getInt(1);

                                    // Inserisco i pasti associati in batch
                                    psMeal.clearBatch();
                                    psMeal.setInt(1, dayId);
                                    for (MealEntity meal : day.getPasti()) {
                                        psMeal.setString(2, meal.getNome());
                                        psMeal.setString(3, meal.getDescrizione());
                                        psMeal.setInt(4, meal.getKcal());
                                        psMeal.addBatch();
                                    }
                                    psMeal.executeBatch();
                                }
                            }
                        }

                    }

                    conn.commit();

                    ramStorage.add(diet);


                } catch (SQLException e) {
                    throw new DietPersistenceException("Errore salvataggio dieta su DB", e);
                }
            }
        }
    }

    public List<DietEntity> getAllDiets() throws DietNotFoundException {
        switch (SessionManagerSLT.getInstance().getPersistenceType()) {
            case RAM -> {
                return loadFromRam();
            }
            case FILESYSTEM -> {
                return loadFromFileSystem();
            }
            case DATABASE -> {
                return loadFromDatabase();
            }
        }
        return Collections.emptyList();
    }

    private List<DietEntity> loadFromRam() throws DietNotFoundException {
        if (ramStorage.isEmpty()) {
            throw new DietNotFoundException("Nessuna dieta trovata in RAM");
        }
        return new ArrayList<>(ramStorage);
    }

    private List<DietEntity> loadFromFileSystem() throws DietNotFoundException {
        List<DietEntity> loaded = loadAllDietsList();
        if (loaded.isEmpty()) {
            throw new DietNotFoundException("Nessuna dieta salvata su FileSystem");
        }
        ramStorage.clear();
        ramStorage.addAll(loaded);
        return loaded;
    }

    private List<DietEntity> loadFromDatabase() throws DietNotFoundException {
        String url = "jdbc:mysql://localhost:3306/mio_database";
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        String selectDietsSQL = "SELECT id, nome, descrizione, durata, nutritionist_username FROM diets";
        String selectDaysSQL = "SELECT id, giorno FROM diet_days WHERE diet_id = ?";
        String selectMealsSQL = "SELECT nome, descrizione, kcal FROM meals WHERE day_id = ?";

        List<DietEntity> result = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement psDiets = conn.prepareStatement(selectDietsSQL)) {

            ResultSet rsDiets = psDiets.executeQuery();

            while (rsDiets.next()) {
                DietEntity diet = buildDietFromResultSet(rsDiets, conn, selectDaysSQL, selectMealsSQL);
                result.add(diet);
            }

            if (result.isEmpty()) {
                throw new DietNotFoundException("Nessuna dieta trovata nel Database");
            }

            ramStorage.clear();
            ramStorage.addAll(result);

            return result;

        } catch (SQLException e) {
            throw new DietNotFoundException("Errore durante il recupero delle diete da DB: " + e.getMessage());
        }
    }

    private DietEntity buildDietFromResultSet(ResultSet rsDiets, Connection conn, String selectDaysSQL, String selectMealsSQL) throws SQLException {
        DietEntity diet = new DietEntity();
        diet.setNome(rsDiets.getString("nome"));
        diet.setDescrizione(rsDiets.getString("descrizione"));
        diet.setDurata(rsDiets.getInt("durata"));
        diet.setNutritionistUsername(rsDiets.getString("nutritionist_username"));

        int dietId = rsDiets.getInt("id");

        List<DayEntity> giorni = new ArrayList<>();
        try (PreparedStatement psDays = conn.prepareStatement(selectDaysSQL)) {
            psDays.setInt(1, dietId);
            ResultSet rsDays = psDays.executeQuery();

            while (rsDays.next()) {
                DayEntity day = new DayEntity(rsDays.getInt("giorno"));
                int dayId = rsDays.getInt("id");

                List<MealEntity> pasti = new ArrayList<>();
                try (PreparedStatement psMeals = conn.prepareStatement(selectMealsSQL)) {
                    psMeals.setInt(1, dayId);
                    ResultSet rsMeals = psMeals.executeQuery();

                    while (rsMeals.next()) {
                        MealEntity meal = new MealEntity(
                                rsMeals.getString("nome"),
                                rsMeals.getString("descrizione"),
                                rsMeals.getInt("kcal")
                        );
                        pasti.add(meal);
                    }
                }
                day.setPasti(pasti);
                giorni.add(day);
            }
        }
        diet.setGiorni(giorni);
        return diet;
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

}
