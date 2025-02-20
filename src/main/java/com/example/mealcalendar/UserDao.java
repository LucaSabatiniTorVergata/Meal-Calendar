package com.example.mealcalendar;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

public class UserDao implements UserDaoInterface {

    private static final String DIR="/Users/lucasabatini/git/ispw/Meal-Calendar/";
    private static final String FILE_PATH = DIR + "users.txt";  // Path for file storage
    private boolean useDatabase;  // Flag for DB usage
    private boolean useDemo;  // Flag for de   mo mode

    private static String url;
    private static String dbuser;
    private static String password;

    // Logger setup
    private static final Logger logger = Logger.getLogger(UserDao.class.getName());

    // In-memory user list for demo mode
    private static List<UserEntity> demoUsers = new ArrayList<>();

    // Load database properties
    static {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("src/main/resources/sqlData.properties")) {
            props.load(input);
            url = props.getProperty("db.url");
            dbuser = props.getProperty("db.dbuser");
            password = props.getProperty("db.password");
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error loading database properties", ex);
        }
    }

    public UserDao(boolean useDatabase, boolean useDemo) {
        this.useDemo = useDemo;
        this.useDatabase = useDatabase;
        if (!useDatabase && !useDemo) {
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
                logger.log(Level.SEVERE, "Error creating file", e);
            }
        }
    }

    @Override
    public boolean registerUser(UserEntity user) throws IOException {
        try {
            if (useDatabase) {
                return registerUserDB(user);  // Use DB
            } else if (useDemo) {
                return registerUserDemo(user);  // Use in-memory demo
            } else {
                return registerUserFS(user);  // Use File System
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error registering user", e);
            return false;  // Return false on exception
        }
    }

    // Register user in file system


    // Register user in demo mode (in-memory)
    private boolean registerUserDemo(UserEntity user) {
        demoUsers.add(user);
        return true;  // Simply add the user to the demo list
    }

    @Override
    public List<UserEntity> getAllUsers() throws IOException {
        try {
            if (useDemo) {
                return getAllUsersDemo();  // Return demo users list
            } else if (useDatabase) {
                return getAllUsersDB();  // Fetch users from DB
            } else {
                return getAllUsersFS();  // Fetch users from file system
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving all users", e);
        }
        return new ArrayList<>();  // Return empty list in case of error
    }

    // Fetch all users from file system
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

    // Fetch all users from database
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

    // Fetch all users from demo (in-memory)
    private List<UserEntity> getAllUsersDemo() {
        return new ArrayList<>(demoUsers);  // Return a copy of the demo users list
    }

    @Override
    public UserEntity getUserByUsername(String username) throws IOException {
        try {
            if (useDemo) {
                return getUserByUsernameDemo(username);  // Search in demo list
            } else if (useDatabase) {
                return getUserByUsernameDB(username);  // Search in DB
            } else {
                return getUserByUsernameFS(username);  // Search in file system
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving user by username", e);
        }
        return null;  // Return null if not found
    }

    // Search user in demo (in-memory)
    private UserEntity getUserByUsernameDemo(String username) {
        for (UserEntity user : demoUsers) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;  // Return null if not found
    }

    // Search user in file system
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
        return null;  // Return null if not found
    }

    // Search user in database
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
        return null;  // Return null if not found
    }

    private boolean registerUserFS(UserEntity user) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(user.getUsername() + ":" + user.getEmail() + ":" + user.getPassword());
            writer.newLine();
        }
        return true;
    }

    // Register user in database
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
}