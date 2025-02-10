package com.example.mealcalendar;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.io.FileInputStream;

public class UserDao implements UserDaoInterface {

    private static final String FILE_PATH = "users.txt";
    private static final boolean USE_DATABASE = true; // Cambia a true per usare il database

    // Configurazione Database (da cambiare con i tuoi dati)
    private static String url;
    private static String dbuser;
    private static String password;

    static {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("src/main/resources/sqlData.properties")) {
            props.load(input);
            url = props.getProperty("db.url");
            dbuser = props.getProperty("db.dbuser");
            password = props.getProperty("db.password");
        } catch (IOException ex) {
            ex.printStackTrace();
            // Aggiungi gestione dell'errore se il file non viene trovato
        }
    }



    public UserDao(boolean usedatabase) {
        if (!USE_DATABASE) {
            File file = new File(FILE_PATH);
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean registerUser(UserEntity user) throws IOException {
        try {
            if (USE_DATABASE) {
                return registerUserDB(user);
            } else {
                return registerUserFS(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // 🔥 Aggiunto return false in caso di eccezione
        }
    }

    private boolean registerUserFS(UserEntity user) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(user.getUsername() + ":" + user.getEmail() + ":" + user.getPassword() + "\n");
        }
        return true;
    }

    private boolean registerUserDB(UserEntity user) throws SQLException {
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, dbuser, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            System.out.println("password mandata al db register:"+ user.getPassword());
            stmt.setString(3, user.getPassword());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // 🔥 Aggiunto return false in caso di eccezione
        }
    }

    @Override
    public List<UserEntity> getAllUsers() throws IOException {
        try {
            return USE_DATABASE ? getAllUsersDB() : getAllUsersFS();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>(); // 🔥 Aggiunto return di una lista vuota in caso di errore
        }
    }

    private List<UserEntity> getAllUsersFS() throws IOException {
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

    private List<UserEntity> getAllUsersDB() throws SQLException {
        List<UserEntity> users = new ArrayList<>();
        String sql = "SELECT username, email, password FROM users";
        try (Connection conn = DriverManager.getConnection(url, dbuser, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(new UserEntity(rs.getString("username"), rs.getString("email"), rs.getString("password")));
            }
        }
        return users;
    }

    @Override
    public UserEntity getUserByUsername(String username) throws IOException {
        try {
            return USE_DATABASE ? getUserByUsernameDB(username) : getUserByUsernameFS(username);
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // 🔥 Aggiunto return null in caso di errore
        }
    }

    private UserEntity getUserByUsernameFS(String username) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userParts = line.split(":");
                if (userParts.length >= 3 && userParts[0].trim().equalsIgnoreCase(username.trim())) {
                    return new UserEntity(userParts[0], userParts[1], userParts[2]);
                }
            }
        }
        return null;
    }

    private UserEntity getUserByUsernameDB(String username) throws SQLException {
        String sql = "SELECT username, email, password FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(url, dbuser, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new UserEntity(rs.getString("username"), rs.getString("email"), rs.getString("password"));
                }
            }
        }
        return null;
    }
}
