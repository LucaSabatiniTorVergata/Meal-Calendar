package com.example.mealcalendar;

import java.io.*;
import java.sql.*;
import java.util.*;

public class UserDao implements UserDaoInterface {

    private static final String FILE_PATH = "users.txt";  // Path for file storage
    private static boolean USE_DATABASE;  // Flag for DB usage
    private static boolean USE_DEMO;  // Flag for demo mode

    private static String url;
    private static String dbuser;
    private static String password;

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
            if (USE_DATABASE) {
                return registerUserDB(user);  // Use DB
            } else if (USE_DEMO) {
                return registerUserDemo(user);  // Use in-memory demo
            } else {
                return registerUserFS(user);  // Use File System
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Return false on exception
        }
    }

    // Register user in file system
    private boolean registerUserFS(UserEntity user) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(user.getUsername() + ":" + user.getEmail() + ":" + user.getPassword() + "\n");
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

    // Register user in demo mode (in-memory)
    private boolean registerUserDemo(UserEntity user) {
        demoUsers.add(user);
        return true;  // Simply add the user to the demo list
    }

    @Override
    public List<UserEntity> getAllUsers() throws IOException {
        try {
            if (USE_DEMO) {
                return getAllUsersDemo();  // Return demo users list
            } else if (USE_DATABASE) {
                return getAllUsersDB();  // Fetch users from DB
            } else {
                return getAllUsersFS();  // Fetch users from file system
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            if (USE_DEMO) {
                return getUserByUsernameDemo(username);  // Search in demo list
            } else if (USE_DATABASE) {
                return getUserByUsernameDB(username);  // Search in DB
            } else {
                return getUserByUsernameFS(username);  // Search in file system
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
}
