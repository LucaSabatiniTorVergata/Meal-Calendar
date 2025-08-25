package com.example.mealcalendar.testLuca;

import com.example.mealcalendar.bean.RistoranteBean;
import com.example.mealcalendar.model.TipologiaRistorante;
import com.example.mealcalendar.register_login.RegistrationController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class RegistrationControllerTest {

    private RegistrationController controller;

    @BeforeEach
    void setUp() {
        controller = new RegistrationController();
    }

    @Test
    void testRegisterRistorante() {
        RistoranteBean ristorante = new RistoranteBean("Test Ristorante", "Via Test 1", 50, TipologiaRistorante.ONNIVORO);
        assertDoesNotThrow(() -> controller.registerRistorante(ristorante));
    }
}
