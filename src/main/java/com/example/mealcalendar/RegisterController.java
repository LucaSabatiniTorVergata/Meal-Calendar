package com.example.mealcalendar;

import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;
import java.util.List;

public class RegisterController {

    // Il controller utilizza il DAO
    private UserDaoFS userDAO = new UserDaoFS();

    // Metodo per verificare se l'username esiste già (logica nel controller)
    public boolean usernameExists(String username) throws IOException {
        List<UserEntity> users = userDAO.getAllUsers();
        for (UserEntity user : users) {
            if (user.getUsername().equals(username)) {
                return true; // L'username esiste già
            }
        }
        return false; // L'username non esiste
    }

    // Metodo per registrare un utente
    public boolean register(UserBean userRegisterBean) throws IOException {
        // Verifica che l'username non esista già
        if (usernameExists(userRegisterBean.getUsername())) {
            return false; // Non registriamo l'utente perché l'username è già in uso
        }

        // Hash della password (opzionale, se vuoi usarlo)
        String hashedPassword = BCrypt.hashpw(userRegisterBean.getPassword(), BCrypt.gensalt());

        // Crea il nuovo utente
        UserEntity newUser = new UserEntity(userRegisterBean.getUsername(), userRegisterBean.getEmail(), hashedPassword);

        // Registra l'utente utilizzando il DAO
        return userDAO.registerUser(newUser);
    }
}
