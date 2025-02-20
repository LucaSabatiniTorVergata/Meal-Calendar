package com.example.mealcalendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FrigoriferoControllerTest {

    //Smith Fidel Valenzuela Santana

    private FrigoriferoController frigoriferoController;

    @BeforeEach
    void setUp() {
        // Inizializziamo il FrigoriferoController con un InventarioDao senza persistenza su file
        frigoriferoController = new FrigoriferoController(true);
    }

    @Test
    void testAggiungiIngrediente_NuovoIngrediente() {
        frigoriferoController.aggiungiIngrediente("mela", 5);

        Map<String, Integer> inventario = frigoriferoController.getInventario();
        assertTrue(inventario.containsKey("mela"), "L'inventario dovrebbe contenere 'Mela'");
        assertEquals(5, inventario.get("mela"), "La quantità di 'Mela' dovrebbe essere 5");
    }
}