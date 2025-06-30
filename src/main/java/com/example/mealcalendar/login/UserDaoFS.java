package com.example.mealcalendar.login;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoFS implements UserDaoInterface {

    private static final String FILE_PATH = "users.json";
    private static final Logger logger = Logger.getLogger(UserDaoFS.class.getName());
    private final ObjectMapper mapper = new ObjectMapper();


    @Override
    public boolean registerUser(UserEntity user) {
        List<UserEntity> utenti = new ArrayList<>();

        // Prova a caricare quelli esistenti
        try {
            utenti = getAllUsers();
        } catch (Exception e) {
            logger.log(Level.WARNING, "⚠️ Impossibile leggere utenti esistenti. File corrotto?", e);
        }

        // Controllo duplicato
        boolean exists = utenti.stream()
                .anyMatch(u -> u.getUsername().equalsIgnoreCase(user.getUsername()));

        if (exists) {
            logger.warning("⛔ Username già esistente: " + user.getUsername());
            return false;
        }

        utenti.add(user);

        // Scrivi tutti gli utenti nel file (sovrascrittura completa intenzionale)
        try (FileWriter writer = new FileWriter(FILE_PATH, false)) {  // ❗ false = sovrascrivi, ma con lista aggiornata
            mapper.writeValue(writer, utenti);
            logger.info("✅ Utente registrato: " + user.getUsername());
            return true;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "❌ Errore durante la scrittura di users.json", e);
            return false;
        }
    }

    @Override
    public List<UserEntity> getAllUsers() {
        File file = new File(FILE_PATH);

        if (!file.exists() || file.length() == 0) {
            logger.warning("⚠️ File users.json inesistente o vuoto.");
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(file)) {
            return mapper.readValue(reader, new TypeReference<List<UserEntity>>() {});
        } catch (IOException e) {
            logger.log(Level.WARNING, "❌ Errore durante la lettura di users.json", e);
            return new ArrayList<>();
        }
    }

    @Override
    public UserEntity getUserByUsername(String username){
            return getAllUsers().stream()
                    .filter(user -> user.getUsername().equals(username))
                    .findFirst()
                    .orElse(null);
    }

    @Override
    public void updateUser(UserEntity updatedUser) throws IOException {
        List<UserEntity> utenti = getAllUsers();

        // Rimuovi quello vecchio con lo stesso username
        utenti.removeIf(u -> u.getUsername().equals(updatedUser.getUsername()));

        // Aggiungi quello nuovo aggiornato
        utenti.add(updatedUser);

        // Sovrascrivi il file con la lista aggiornata
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            mapper.writeValue(writer, utenti);
        }
    }


}
