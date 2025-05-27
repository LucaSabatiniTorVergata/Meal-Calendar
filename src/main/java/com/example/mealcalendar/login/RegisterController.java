package com.example.mealcalendar.login;

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

        if (userDAO instanceof UserDaoFS) {
            LOGGER.log(Level.INFO,"È un File System DAO!");
            for (UserEntity user : userDAO.getAllUsers()) {
                if (user.getUsername().equalsIgnoreCase(username)) {
                    return true;
                }
            }
        } else if (userDAO instanceof UserDaoDB) {
            LOGGER.log(Level.INFO,"È un Database DAO!");
            for (UserEntity user : userDAO.getAllUsers()) {
                if (user.getUsername().equalsIgnoreCase(username)) {
                    return true;
                }
            }
        } else if (userDAO instanceof UserDaoRam) {
            LOGGER.log(Level.INFO,"È un Demo DAO!");
            for (UserEntity user : userDAO.getAllUsers()) {
                if (user.getUsername().equalsIgnoreCase(username)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Metodo per registrare un nuovo utente
    public boolean register(UserBean userRegisterBean) throws IOException {

        System.out.println(userRegisterBean.getUsername());
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
