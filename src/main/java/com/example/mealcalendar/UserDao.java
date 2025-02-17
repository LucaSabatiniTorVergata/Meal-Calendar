package com.example.mealcalendar;

import java.io.*;
import java.sql.*;
import java.util.*;

public class UserDao implements UserDaoInterface {

    private static final String FILE_PATH = "users.txt";  // Percorso del file per il File System
    private static boolean USE_DATABASE;  // Determina se usare il DB o il File System
    private static boolean USE_DEMO;  // Determina se usare la modalità demo

    // Configurazione Database
    private static String url;
    private static String dbuser;
    private static String password;

    // Lista in memoria per la modalità demo
    private static List<UserEntity> demoUsers = new ArrayList<>();

    static {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("src/main/resources/sqlData.properties")) {
            props.load(input);
            url = props.getProperty("db.url");
            dbuser = props.getProperty("db.dbuser");
            password = props.getProperty("db.password");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public UserDao(boolean useDatabase, boolean useDemo) {
        UserDao.USE_DEMO = useDemo;
        UserDao.USE_DATABASE = useDatabase;
        if (!USE_DATABASE && !USE_DEMO) {
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
            if (USE_DATABASE && !USE_DEMO) {
                return registerUserDB(user);  // Usa il DB
            } else if (!USE_DATABASE && !USE_DEMO) {
                return registerUserFS(user);  // Usa il File System
            } else if (USE_DEMO) {
                return registerUserDemo(user);  // Usa la modalità demo (in memoria)
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Restituisci false in caso di eccezione
        }
        return false;  // Aggiungi un return di default per coprire ogni altro caso
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
            stmt.setString(3, user.getPassword());
            return stmt.executeUpdate() > 0;
        }
    }

    // Metodo per registrare un utente in modalità demo (in memoria)
    private boolean registerUserDemo(UserEntity user) {
        demoUsers.add(user);
        return true;  // In modalità demo, aggiungiamo semplicemente l'utente alla lista
    }

    @Override
    public List<UserEntity> getAllUsers() throws IOException {
        try {
            if (USE_DEMO) {
                return getAllUsersDemo();  // Restituisce la lista demo
            } else if (USE_DATABASE) {
                return getAllUsersDB();
            } else if (!USE_DEMO && !USE_DATABASE) {
                return getAllUsersFS();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(); // Ritorno di lista vuota in caso di errore o se non esistono utenti
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

    // Metodo per ottenere gli utenti demo (in memoria)
    private List<UserEntity> getAllUsersDemo() {
        return new ArrayList<>(demoUsers);  // Restituiamo una copia della lista demo
    }

    @Override
    public UserEntity getUserByUsername(String username) throws IOException {
        try {
            if (USE_DEMO) {
                return getUserByUsernameDemo(username);  // Cerca nell'elenco demo
            } else if (USE_DATABASE) {
                return getUserByUsernameDB(username);  // Usa il DB
            } else if (!USE_DEMO && !USE_DATABASE) {
                return getUserByUsernameFS(username);  // Usa il File System
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Ritorno null se non trovato
    }

    // Metodo per cercare un utente nella lista demo (in memoria)
    private UserEntity getUserByUsernameDemo(String username) {
        for (UserEntity user : demoUsers) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;  // Restituisce null se l'utente non viene trovato
    }

    // Implementazione del metodo per il File System
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
        return null;  // Restituisce null se non trovato
    }

    // Implementazione del metodo per il Database
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
        return null;  // Restituisce null se non trovato
    }
}
