package com.example.mealcalendar;

import java.io.*;
import java.util.*;

public class UserDaoFS {

    private static final String FILE_PATH = "users.txt";

    public UserDaoFS() {
        File file = new File(FILE_PATH);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metodo per registrare un utente (senza controllo sull'esistenza dell'username)
    public boolean registerUser(UserEntity user) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(user.getUsername() + ":" + user.getEmail() + ":" + user.getPassword() + "\n");
        }
        return true; // Se siamo qui, l'utente è stato scritto sul file
    }

    // Metodo per ottenere tutti gli utenti dal file
    public List<UserEntity> getAllUsers() throws IOException {
        List<UserEntity> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userParts = line.split(":");
                if (userParts.length >= 3) {
                    users.add(new UserEntity(userParts[0], userParts[1], userParts[2]));
                }
            }
        }
        return users;
    }

    // Puoi lasciare anche il metodo getUserByUsername se ti serve
    public UserEntity getUserByUsername(String username) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userParts = line.split(":");
                if (userParts.length >= 3) {
                    String fileUsername = userParts[0];
                    String email = userParts[1];
                    String hashedPassword = userParts[2];
                    if (fileUsername.trim().equals(username.trim())) {
                        return new UserEntity(fileUsername, email, hashedPassword);
                    }
                }
            }
        }
        return null;
    }
}
