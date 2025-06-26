package com.example.mealcalendar.login;


import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoFS implements UserDaoInterface {

    private static final String FILE_PATH    = "users.txt";
    private static final Logger logger = Logger.getLogger(UserDaoFS.class.getName());


    @Override
    public boolean registerUser(UserEntity user) throws IOException {
        File file = new File(FILE_PATH);
        try {
            if (!file.exists()) {
                boolean fileCreated = file.createNewFile();
                if (fileCreated) {
                    logger.info("File created successfully.");
                } else {
                    logger.info("File already exists.");
                }
            }
        } catch (IOException e) {
            exceptionHandler();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String riga = user.getUsername() + ":" + user.getEmail() + ":" + user.getPassword() + ":" + user.getRole();
            writer.write(riga);
            writer.newLine();
            return true;
        } catch (IOException e) {
            logger.info("❌ Errore durante la scrittura: ");
        }

        return false;
    }

    @Override
    public List<UserEntity> getAllUsers() throws IOException {
        List<UserEntity> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userParts = line.split(":");
                if (userParts.length >= 3) {
                    users.add(new UserEntity(userParts[0], userParts[1], userParts[2],userParts[3]));
                }
            }
        }
        return users;
    }

    @Override
    public UserEntity getUserByUsername(String username) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userParts = line.split(":");
                if (userParts.length >= 3 && userParts[0].trim().equalsIgnoreCase(username.trim())) {
                    return new UserEntity(userParts[0], userParts[1], userParts[2],userParts[3]);
                }
            }
        }
        return null;
    }

    private void exceptionHandler(){
        logger.log(Level.SEVERE, "Errore nella creazione del file tento di crearne uno nuovo");
        File file = new File(FILE_PATH);
        try {
            if (!file.exists()) {
                boolean fileCreated = file.createNewFile();
                if (fileCreated) {
                    logger.info("File created successfully.");
                } else {
                    logger.info("File already exists.");
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE,"impossibile creare il file");
        }

    }

}
