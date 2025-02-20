package com.example.mealcalendar;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

class LoginControllerTest {

    private static String url;
    private static String dbuser;
    private static String password;

    private static final Logger logger = Logger.getLogger(UserDao.class.getName());

    private LoginController loginController;
    private UserDao userDao;
    private static final Logger LOGGER = Logger.getLogger(LoginControllerTest.class.getName());

    @BeforeEach
    void setUp() throws IOException, SQLException {
        userDao = new UserDao(true, false); // Usa il database
        loginController = new LoginController(userDao);

        // Creazione di un utente di test nel database
        String username = "testUser";
        String password = "securePassword";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));

        // Cancella l'utente se già esistente
        UserEntity existingUser = userDao.getUserByUsername(username);
        if (existingUser != null) {
            LOGGER.log(Level.INFO, "Deleting existing user...");
            deleteTestUser(username);
        }

        // Registra il nuovo utente di test
        userDao.registerUser(new UserEntity(username, "test@example.com", hashedPassword));

        // Verifica che l'utente sia stato salvato con la password corretta
        UserEntity savedUser = userDao.getUserByUsername(username);
        assertNotNull(savedUser, "Test user should be created");
        assertTrue(BCrypt.checkpw(password, savedUser.getPassword()), "Stored password should match the original");
    }

    @Test
    void testLoginSuccess() throws IOException {
        // Arrange
        String username = "testUser";
        String password = "securePassword";
        LoginBean loginBean = new LoginBean(username, password);

        // Act
        boolean result = loginController.login(loginBean);

        // Assert
        assertTrue(result, "Login should succeed with correct credentials");
    }

    @Test
    void testLoginFailure_WrongPassword() throws IOException {
        // Arrange
        String username = "testUser";
        String wrongPassword = "wrongPassword";
        LoginBean loginBean = new LoginBean(username, wrongPassword);

        // Act
        boolean result = loginController.login(loginBean);

        // Assert
        assertFalse(result, "Login should fail with incorrect password");
    }

    @Test
    void testLoginFailure_UserNotFound() throws IOException {
        // Arrange
        String username = "nonExistentUser";
        String password = "password";
        LoginBean loginBean = new LoginBean(username, password);

        // Act
        boolean result = loginController.login(loginBean);

        // Assert
        assertFalse(result, "Login should fail when user does not exist");
    }

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


    private void deleteTestUser(String username) throws SQLException {
        String sql = "DELETE FROM users WHERE username = ?";


        try (Connection conn = DriverManager.getConnection(url, dbuser, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
    }
}