package com.example.mealcalendar;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FindRestaurantControllerTest {

    private ChooseRestaurantController controller;
    private RestaurantListSLTPat listaRistoranti;

    @BeforeEach
    void setUp() {
        // Crea un'istanza di RestaurantListSLTPat per testare la logica
        listaRistoranti = RestaurantListSLTPat.getInstance();
        listaRistoranti.svuotaLista();

        // Crea i filtri di ricerca
        FiltersRestaurantBean filters = new FiltersRestaurantBean();
        filters.setDistanza(2);
        filters.setTipoDieta("vegan");
        filters.setPasto("lunch");

        controller = new ChooseRestaurantController(filters);
    }

    @Test
    void testTrovaRistorante_ParsingSimulato() {
        // Simuliamo una risposta JSON dell'API di Google
        String jsonResponse = "{ \"results\": [" +
                "{ \"name\": \"Ristorante Vegano Roma\", \"vicinity\": \"Via Roma 1\", " +
                "\"geometry\": { \"location\": { \"lat\": 41.9001, \"lng\": 12.4901 } } }," +
                "{ \"name\": \"Bistro Bio\", \"vicinity\": \"Piazza Venezia\", " +
                "\"geometry\": { \"location\": { \"lat\": 41.9025, \"lng\": 12.4960 } } }" +
                "] }";

        JSONObject json = new JSONObject(jsonResponse);
        JSONArray results = json.getJSONArray("results");

        for (int i = 0; i < results.length(); i++) {
            JSONObject ristorante = results.getJSONObject(i);
            String nome = ristorante.getString("name");
            String indirizzo = ristorante.optString("vicinity", "Indirizzo non disponibile");
            double lat = ristorante.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
            double lng = ristorante.getJSONObject("geometry").getJSONObject("location").getDouble("lng");

            listaRistoranti.aggiungiRistorante(RestaurantFactory.creaRistorante(nome, indirizzo, lat, lng));
        }

        // Recuperiamo la lista dei ristoranti elaborata
        List<ReturnRestaurantsBean> ristoranti = listaRistoranti.getBeansList();

        assertEquals(2, ristoranti.size(), "Dovrebbero esserci 2 ristoranti nella lista");
        assertEquals("Ristorante Vegano Roma", ristoranti.get(0).getNome(), "Il primo ristorante dovrebbe essere 'Ristorante Vegano Roma'");
        assertEquals("Bistro Bio", ristoranti.get(1).getNome(), "Il secondo ristorante dovrebbe essere 'Bistro Bio'");
    }
}