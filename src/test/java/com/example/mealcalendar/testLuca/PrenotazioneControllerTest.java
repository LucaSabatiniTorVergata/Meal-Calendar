package com.example.mealcalendar.testLuca;

import com.example.mealcalendar.bean.PrenotazioneBean;
import com.example.mealcalendar.controller_applicativo.PrenotazioneController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrenotazioneControllerTest {

    private PrenotazioneController controller;

    @BeforeEach
    void setUp() {
        controller = new PrenotazioneController();
    }

    @Test
    void testGetPrenotazioni() {
        List<PrenotazioneBean> prenotazioni = controller.getPrenotazioni();
        assertNotNull(prenotazioni, "La lista delle prenotazioni non deve essere null");
        // Non possiamo garantire che ci siano prenotazioni, ma la lista deve essere corretta
        prenotazioni.forEach(p -> assertNotNull(p.getUsernameUtente(), "Ogni prenotazione deve avere un username"));
    }
}
