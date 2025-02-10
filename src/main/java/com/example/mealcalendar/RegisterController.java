package com.example.mealcalendar;

import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;
import java.util.List;

public class RegisterController {

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
        System.out.println("password prima dell'hash"+userRegisterBean.getPassword());
        String hashedPassword = BCrypt.hashpw(userRegisterBean.getPassword(), BCrypt.gensalt());
        System.out.println("dal register controller:" + hashedPassword);
        // Creazione dell'oggetto utente
        UserEntity newUser = new UserEntity(userRegisterBean.getUsername(), userRegisterBean.getEmail(), hashedPassword);

        // Registrazione utente tramite il DAO
        return userDAO.registerUser(newUser);
    }
}
