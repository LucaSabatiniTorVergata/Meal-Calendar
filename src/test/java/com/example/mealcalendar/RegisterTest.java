package com.example.mealcalendar;


import com.example.mealcalendar.login.RegisterController;
import com.example.mealcalendar.login.UserBean;
import com.example.mealcalendar.login.UserDao;
import com.example.mealcalendar.login.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegisterTest {

    //Luca Sabatini

    private RegisterController registerController;
    private UserDao userDao;

    // Impostiamo i test prima di ogni test case
    @BeforeEach
    void setUp() {
        // Usa il file system (useDemo = false, useDatabase = false)
        userDao = new UserDao(false, false);
        registerController = new RegisterController(userDao);
    }


    @Test
    void testRegisterUserWithExistingUsername() throws IOException {
        // Crea un UserBean per il test
        UserBean userBean = new UserBean("existinguser", "existinguser@example.com", "password123");

        // Esegui la registrazione
        registerController.register(userBean);

        // Proviamo a registrare un altro utente con lo stesso username
        UserBean anotherUser = new UserBean("existinguser", "another@example.com", "password456");
        boolean result = registerController.register(anotherUser);

        // Verifica che il risultato sia false, poiché l'username è già in uso
        assertFalse(result);
    }

    @Test
    void testRegisterUserInDemoMode() throws IOException {
        // Passiamo alla modalità demo (in-memory)
        userDao = new UserDao(false, true);  // useDemo = true
        registerController = new RegisterController(userDao);

        // Crea un UserBean per il test
        UserBean userBean = new UserBean("newuserdemo", "demo@example.com", "password123");

        // Esegui la registrazione
        boolean result = registerController.register(userBean);

        // Verifica che la registrazione sia andata a buon fine
        assertTrue(result);

        // Verifica che l'utente sia stato effettivamente registrato nella modalità demo
        List<UserEntity> allUsers = userDao.getAllUsers();
        assertTrue(allUsers.stream().anyMatch(user -> user.getUsername().equals("newuserdemo")));
    }

    @Test
    void testRegisterUserFailsWithExistingUsernameInDemoMode() throws IOException {
        // Passiamo alla modalità demo (in-memory)
        userDao = new UserDao(false, true);  // useDemo = true
        registerController = new RegisterController(userDao);

        // Crea e registra un utente
        UserBean userBean = new UserBean("userdemo", "demo@example.com", "password123");
        registerController.register(userBean);

        // Proviamo a registrare un altro utente con lo stesso username
        UserBean anotherUser = new UserBean("userdemo", "demo2@example.com", "password456");
        boolean result = registerController.register(anotherUser);

        // Verifica che la registrazione fallisca (l'username è già stato registrato)
        assertFalse(result);
    }
}
