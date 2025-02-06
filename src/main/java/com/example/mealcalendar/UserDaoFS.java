package com.example.mealcalendar;

import java.io.*;
import java.util.*;
import org.mindrot.jbcrypt.BCrypt;

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

    public boolean registerUser(UserEntity user) throws IOException {
        // Verifica se l'username esiste già
        if (usernameExists(user.getUsername())) {
            return false; // Username già esistente, non registriamo l'utente
        }

        // Se l'username non esiste, procediamo con la registrazione
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(user.getUsername() + ":" + user.getEmail() + ":" + user.getPassword() + "\n");
        }

        return true; // Utente registrato con successo
    }

    // Metodo per verificare se l'username esiste già
    private boolean usernameExists(String username) throws IOException {
        List<UserEntity> users = getAllUsers();
        for (UserEntity user : users) {
            if (user.getUsername().equals(username)) {
                return true; // L'username esiste già
            }
        }
        return false; // L'username non esiste
    }

    // Metodo per ottenere tutti gli utenti dal file (per verificare l'esistenza dell'username)
    private List<UserEntity> getAllUsers() throws IOException {
        List<UserEntity> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userParts = line.split(":");
                users.add(new UserEntity(userParts[0], userParts[1], userParts[2]));
            }
        }
        return users;
    }

    // Metodo per recuperare un utente tramite username
    public UserEntity getUserByUsername(String username) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Scanner scanner = new Scanner(line);
                scanner.useDelimiter(":");  // Imposta il separatore

                if (scanner.hasNext()) {
                    String fileUsername = scanner.next();
                    String email = scanner.hasNext() ? scanner.next() : "";
                    String hashedPassword = scanner.hasNext() ? scanner.next() : "";
                    if (Objects.equals(fileUsername.trim(), username.trim())){
                        return new UserEntity(fileUsername, email, hashedPassword);
                    }
                }
                scanner.close();
            }
        }
        return null;
    }

        public boolean checkPassword (String username, String password) throws IOException {

            UserEntity user = getUserByUsername(username);


            if (user != null) {
                // Confronta la password criptata con quella inserita
                System.out.println("Password inserita: " + password);
                System.out.println("Password memorizzata: " + user.getPassword());
                System.out.println("Password corretta: " + BCrypt.checkpw(password, user.getPassword()));
                return BCrypt.checkpw(password, user.getPassword());

            }

            return false;
        }

}

