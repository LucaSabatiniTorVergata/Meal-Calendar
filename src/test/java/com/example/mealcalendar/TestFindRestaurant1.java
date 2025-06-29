package com.example.mealcalendar;


import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class TestFindRestaurant1 {

    @Test
    void testRistorantePresenteInLista() throws IOException {
        // Filtro fittizio
        FiltersRestaurantBean filtro = new FiltersRestaurantBean();

        // Stub dell'API con un solo ristorante
        FindRestaurantApiBoundary apiStub = new FindRestaurantApiBoundary() {
            @Override
            public List<JSONObject> cercaRistoranti(FiltersRestaurantBean filtro) {
                JSONObject location = new JSONObject()
                        .put("lat", 40.7128)
                        .put("lng", -74.0060);

                JSONObject geometry = new JSONObject()
                        .put("location", location);

                JSONObject ristorante = new JSONObject()
                        .put("name", "Trattoria New York")
                        .put("vicinity", "Broadway 123")
                        .put("geometry", geometry);

                return Collections.singletonList(ristorante);
            }
        };

        // Creo il controller
        ChooseRestaurantController controller = new ChooseRestaurantController(filtro, apiStub);

        // Eseguo il metodo
        List<ReturnRestaurantsBean> risultati = controller.trovaRistorante();

        // Verifiche
        assertFalse(risultati.isEmpty(), "La lista dei ristoranti non dovrebbe essere vuota");

        boolean trovato = risultati.stream()
                .anyMatch(bean -> "Trattoria New York".equals(bean.getNome()));

        assertTrue(trovato, "Il ristorante 'Trattoria New York' dovrebbe essere presente nella lista");
    }
}