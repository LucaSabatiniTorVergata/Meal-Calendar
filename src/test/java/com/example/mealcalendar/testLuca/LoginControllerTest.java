package com.example.mealcalendar.testLuca;

import com.example.mealcalendar.exception.LoginException;
import com.example.mealcalendar.register_login.LoginController;
import com.example.mealcalendar.register_login.UserLoginBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {

    private LoginController controller;

    @BeforeEach
    void setUp() {
        controller = new LoginController();
    }

    @Test
    void testValloginUtente() throws LoginException {
        UserLoginBean user = new UserLoginBean("testUser", "password", "user");
        boolean result = controller.vallogin(user);
        // Poiché non c'è alcun utente registrato, ci aspettiamo false
        assertFalse(result);
    }

    @Test
    void testValloginRistorante() throws LoginException {
        UserLoginBean ristorante = new UserLoginBean("DemoRistorante", "password", "restaurant");
        boolean result = controller.vallogin(ristorante);
        // Deve essere true se la login crea il demo ristorante
        assertTrue(result);
    }
}
