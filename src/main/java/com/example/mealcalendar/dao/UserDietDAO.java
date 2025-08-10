package com.example.mealcalendar.dao;


import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.model.DietEntity;
import com.example.mealcalendar.model.UserEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class UserDietDAO {


    private static UserDietDAO instance=null;

    private final List<UserEntity> ramStorage = new ArrayList<>();

    private static final String FILE_PATH = "usersdiets.json";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public UserDietDAO() {

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("[]");
            } catch (IOException e) {
                throw new IllegalStateException("Impossibile creare il file utenti", e);
            }
        }
    }

    public static synchronized UserDietDAO getInstance() {
        if (instance == null) {
            instance = new UserDietDAO();
        }
        return instance;
    }


    public void saveUser(UserEntity user) {
        switch (SessionManagerSLT.getInstance().getPersistenceType()) {

            case RAM -> {
                int idx = findUserIndexByEmail(user.getEmail());
                if (idx != -1) {
                    ramStorage.set(idx, user);
                } else {
                    ramStorage.add(user);
                }
            }

            case FILESYSTEM -> {
                List<UserEntity> allUsers = loadAllUsers();
                int idx = -1;
                for (int i = 0; i < allUsers.size(); i++) {
                    if (allUsers.get(i).getEmail().equals(user.getEmail())) {
                        idx = i;
                        break;
                    }
                }
                if (idx != -1) {
                    allUsers.set(idx, user);
                } else {
                    allUsers.add(user);
                }

                try (FileWriter writer = new FileWriter(FILE_PATH)) {
                    gson.toJson(allUsers, writer);
                } catch (IOException e) {
                    throw new IllegalStateException("Errore nel salvataggio utenti", e);
                }
            }

            case DATABASE -> throw new UnsupportedOperationException("Database non supportato ancora");
        }
    }

    public UserEntity getUserByEmail(String email) {
        switch (SessionManagerSLT.getInstance().getPersistenceType()) {
            case RAM -> {
                for (UserEntity user : ramStorage) {
                    if (user.getEmail().equals(email)) return user;
                }
                return null;
            }
            case FILESYSTEM -> {
                List<UserEntity> allUsers = loadAllUsers();
                for (UserEntity user : allUsers) {
                    if (user.getEmail().equals(email)) return user;
                }
                return null;
            }
            case DATABASE -> throw new UnsupportedOperationException("Database non supportato ancora");
        }
        return null;
    }

    public List<UserEntity> getAllUsers() {
        switch (SessionManagerSLT.getInstance().getPersistenceType()) {

            case RAM -> {
                return new ArrayList<>(ramStorage);  // ritorna copia lista RAM
            }
            case FILESYSTEM -> {

                List<UserEntity> loaded = loadAllUsers();
                ramStorage.clear();
                ramStorage.addAll(loaded);
                return loaded;

            }
            case DATABASE -> throw new UnsupportedOperationException("DB non supportato");
        }
        return Collections.emptyList();

    }

    public void deleteByUserEmail(String email) {
        switch (SessionManagerSLT.getInstance().getPersistenceType()) {
            case RAM -> {
                ramStorage.removeIf(user -> user.getEmail().equalsIgnoreCase(email));
            }
            case FILESYSTEM -> {
                List<UserEntity> allUsers = loadAllUsers();
                allUsers.removeIf(user -> user.getEmail().equalsIgnoreCase(email));
                try (FileWriter writer = new FileWriter(FILE_PATH)) {
                    gson.toJson(allUsers, writer);
                } catch (IOException e) {
                    throw new IllegalStateException("Errore durante la cancellazione dell'utente", e);
                }
                ramStorage.clear();
                ramStorage.addAll(allUsers);
            }
            case DATABASE -> throw new UnsupportedOperationException("DB non ancora supportato");
        }
    }



    private List<UserEntity> loadAllUsers() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type userListType = new TypeToken<List<UserEntity>>() {}.getType();
            List<UserEntity> users = gson.fromJson(reader, userListType);
            return users != null ? users : new ArrayList<>();
        } catch (IOException e) {
            throw new IllegalStateException("Errore nel caricamento utenti", e);
        }
    }

    private int findUserIndexByEmail(String email) {
        for (int i = 0; i < ramStorage.size(); i++) {
            if (ramStorage.get(i).getEmail().equals(email)) return i;
        }
        return -1;
    }

    public List<UserEntity> getramStorage() {
        return ramStorage;
    }



}
