package com.example.mealcalendar;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegisterController {

    private static final Logger LOGGER = Logger.getLogger(RegisterController.class.getName());
    private UserDaoInterface userDAO;

    public RegisterController() {
        this(UserDaoFactory.createUserDao()); // Usa la factory per ottenere il DAO corretto
    }

    public RegisterController(UserDaoInterface userDao) {
        this.userDAO = userDao;
    }

    // Metodo per verificare se l'username esiste già
    public boolean usernameExists(String username) throws IOException {
        List<UserEntity> users = userDAO.getAllUsers();
        return users.stream().anyMatch(user -> user.getUsername().equals(username));
    }

    // Metodo per registrare un nuovo utente
    public boolean register(UserBean userRegisterBean) throws IOException {
        if (usernameExists(userRegisterBean.getUsername())) {
            return false; // Username già in uso
        }

        // Hash della password con BCrypt
        LOGGER.log(Level.INFO, "Password prima dell''hash: {0}", userRegisterBean.getPassword());
        String hashedPassword = org.mindrot.jbcrypt.BCrypt.hashpw(userRegisterBean.getPassword(),org.mindrot.jbcrypt.BCrypt.gensalt());
        LOGGER.log(Level.INFO, "Password dopo l''hash: {0}", hashedPassword);

        // Creazione dell'oggetto utente
        UserEntity newUser = new UserEntity(userRegisterBean.getUsername(), userRegisterBean.getEmail(), hashedPassword);

        // Registrazione utente tramite il DAO
        return userDAO.registerUser(newUser);
    }
}
