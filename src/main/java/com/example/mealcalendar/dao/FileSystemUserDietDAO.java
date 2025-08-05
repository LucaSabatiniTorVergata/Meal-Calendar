package com.example.mealcalendar.dao;


import com.example.mealcalendar.model.UserEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileSystemUserDietDAO implements UserDietDAO {

    private static final String FILE_PATH = "usersdiets.json";
    private final Gson gson = new Gson();

    public FileSystemUserDietDAO() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("{}");  // file vuoto con struttura JSON base (mappa vuota)
            } catch (IOException e) {
                throw new IllegalArgumentException("Impossibile creare il file JSON", e);
            }
        }
    }

    @Override
    public void saveUser(UserEntity user) {

        List<UserEntity> users = getAllUsers();

        users.removeIf(u -> u.getEmail().equals(user.getEmail()));
        users.add(user);

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        for (UserEntity user : getAllUsers()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<UserEntity> getAllUsers() {

        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<UserEntity>>() {}.getType();
            List<UserEntity> users = gson.fromJson(reader, listType);
            return users != null ? users : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}



