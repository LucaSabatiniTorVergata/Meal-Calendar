package com.example.mealcalendar;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

import org.mindrot.jbcrypt.BCrypt;

public class UserDao implements UserDaoInterface {

    private static final Logger logger = Logger.getLogger(UserDao.class.getName());

    private static final String FILE_PATH = "users.txt";  // Path per il file di storage
    private boolean useDatabase;  // Usa DB?
    private boolean useDemo;      // Demo in-memory?

    // Parametri DB (caricati da file .properties, se serve)
    private static String url;
    private static String dbuser;
    private static String dbpassword;

    // In-memory list per demo
    private static List<UserEntity> demoUsers = new ArrayList<>();

    static {
        // Esempio di caricamento parametri DB
        // (Se non ti serve, rimuovi pure)
        //Properties props = new Properties();
        //try (InputStream input = new FileInputStream("src/main/resources/sqlData.properties")) {
        //    props.load(input);
        //    url = props.getProperty("db.url");
        //    dbuser = props.getProperty("db.dbuser");
        //    dbpassword = props.getProperty("db.password");
        //} catch (IOException ex) {
        //    logger.log(Level.SEVERE, "Error loading database properties", ex);
        //}
    }

    public UserDao(boolean useDatabase, boolean useDemo) {
        this.useDatabase = useDatabase;
        this.useDemo = useDemo;
        if (!useDatabase && !useDemo) {
            File file = new File(FILE_PATH);
            try {
                if (!file.exists()) {
                    if (file.createNewFile()) {
                        logger.info("File creato correttamente.");
                    } else {
                        logger.info("Il file esiste giÃ .");
                    }
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Errore creazione file", e);
            }
        }
    }

    @Override
    public boolean registerUser(UserEntity user) throws IOException {
        try {
            if (useDatabase) {
                return registerUserDB(user);
            } else if (useDemo) {
                return registerUserDemo(user);
            } else {
                return registerUserFS(user);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Errore nella registrazione dell'utente", e);
            return false;
        }
    }

    // ***********************
    // 1) REGISTRAZIONE UTENTE
    // ***********************

    // FileSystem: SALVA L'HASH (NON LA PASSWORD IN CHIARO)
    private boolean registerUserFS(UserEntity user) throws IOException {
        // 1. Generiamo l'hash della password usando BCrypt
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        // 2. Scriviamo su file: username : email : hashedPassword
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(user.getUsername() + ":" + user.getEmail() + ":" + hashedPassword);
            writer.newLine();
        }
        return true;
    }

    // In-memory
    private boolean registerUserDemo(UserEntity user) {
        // Anche qui salviamo solo hash
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        // Creiamo un nuovo userEntity con password hashed
        UserEntity hashedUser = new UserEntity(user.getUsername(), user.getEmail(), hashedPassword);
        demoUsers.add(hashedUser);
        return true;
    }

    // DB
    private boolean registerUserDB(UserEntity user) throws SQLException {
        // Anche qui, meglio salvare l'hash nel DB
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, dbuser, dbpassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, hashedPassword);  // NON in chiaro
            return stmt.executeUpdate() > 0;
        }
    }

    // *************************
    // 2) RECUPERO TUTTI GLI UTENTI
    // *************************
    @Override
    public List<UserEntity> getAllUsers() throws IOException {
        try {
            if (useDemo) {
                return new ArrayList<>(demoUsers);
            } else if (useDatabase) {
                return getAllUsersDB();
            } else {
                return getAllUsersFS();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Errore nel recupero utenti", e);
            return Collections.emptyList();
        }
    }

    // File System
    private List<UserEntity> getAllUsersFS() throws IOException {
        List<UserEntity> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userParts = line.split(":");
                if (userParts.length == 3) {
                    // username : email : hashedPassword
                    users.add(new UserEntity(userParts[0], userParts[1], userParts[2]));
                }
            }
        }
        return users;
    }

    // DB
    private List<UserEntity> getAllUsersDB() throws SQLException {
        List<UserEntity> users = new ArrayList<>();
        String sql = "SELECT username, email, password FROM users";
        try (Connection conn = DriverManager.getConnection(url, dbuser, dbpassword);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                users.add(new UserEntity(
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password") // hashed password
                ));
            }
        }
        return users;
    }

    // *************************
    // 3) RECUPERO UTENTE SINGOLO
    // *************************
    @Override
    public UserEntity getUserByUsername(String username) throws IOException {
        try {
            if (useDemo) {
                return getUserByUsernameDemo(username);
            } else if (useDatabase) {
                return getUserByUsernameDB(username);
            } else {
                return getUserByUsernameFS(username);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Errore nel recupero utente singolo", e);
        }
        return null;
    }

    private UserEntity getUserByUsernameDemo(String username) {
        for (UserEntity user : demoUsers) {
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
                if (userParts.length == 3 && userParts[0].equalsIgnoreCase(username)) {
                    return new UserEntity(userParts[0], userParts[1], userParts[2]);
                }
            }
        }
        return null;
    }

    private UserEntity getUserByUsernameDB(String username) throws SQLException {
        String sql = "SELECT username, email, password FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(url, dbuser, dbpassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new UserEntity(rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("password")); // hashed password
                }
            }
        }
        return null;
    }
}