package com.example.mealcalendar;

import java.io.*;
import java.sql.*;
import java.util.*;

public class UserDao implements UserDaoInterface {

    private static final String FILE_PATH = "users.txt";  // Percorso del file per il File System
    private static boolean USE_DATABASE;  // Determina se usare il DB
    private static boolean USE_DEMO;  // Determina se usare la modalità demo (ArrayList)

    // Configurazione Database
    private static String url;
    private static String dbuser;
    private static String password;

    // ArrayList per la modalità demo
    private static List<UserEntity> demoUserList = new ArrayList<>();

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
        UserDao.USE_DATABASE = useDatabase;
        UserDao.USE_DEMO = useDemo;

        // Se si sceglie la modalità demo, non fare altro
        if (USE_DEMO) return;

        // Se non si usa il DB, si usa il File System
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
            if (USE_DEMO) {
                return registerUserDemo(user);  // Usa ArrayList in modalità demo
            } else if (USE_DATABASE) {
                return registerUserDB(user);
            } else {
                return registerUserFS(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Metodo per registrare un utente in modalità demo
    private boolean registerUserDemo(UserEntity user) {
        // Controlla se l'utente esiste già (per evitare duplicati)
        for (UserEntity u : demoUserList) {
            if (u.getUsername().equalsIgnoreCase(user.getUsername())) {
                return false;  // Utente già esistente
            }
        }
        // Aggiunge l'utente alla lista
        demoUserList.add(user);
        return true;
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

    @Override
    public List<UserEntity> getAllUsers() throws IOException {
        try {
            if (USE_DEMO) {
                return new ArrayList<>(demoUserList);  // Ritorna una copia della lista demo
            } else if (USE_DATABASE) {
                return getAllUsersDB();
            } else {
                return getAllUsersFS();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
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
            if (USE_DEMO) {
                return getUserByUsernameDemo(username);  // Usa la modalità demo
            } else if (USE_DATABASE) {
                return getUserByUsernameDB(username);
            } else {
                return getUserByUsernameFS(username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Metodo per ottenere un utente in modalità demo
    private UserEntity getUserByUsernameDemo(String username) {
        for (UserEntity user : demoUserList) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
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