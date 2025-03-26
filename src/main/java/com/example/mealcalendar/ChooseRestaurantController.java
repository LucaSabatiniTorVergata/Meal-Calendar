package com.example.mealcalendar;


import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;


//coordinate di roma: 41.9028,12.4964
public class ChooseRestaurantController {
    private final FiltersRestaurantBean filtro;
    private final FindRestaurantApiBoundary googlePlacesBoundary;

    public ChooseRestaurantController(FiltersRestaurantBean filtro,FindRestaurantApiBoundary boundary) {
        this.filtro = filtro;
        this.googlePlacesBoundary = boundary;  // Dependency Injection
    }

    public List<ReturnRestaurantsBean> trovaRistorante() throws IOException {
        List<JSONObject> results = googlePlacesBoundary.cercaRistoranti(filtro);
        RestaurantListSLTPat listaRistoranti = RestaurantListSLTPat.getInstance();
        listaRistoranti.svuotaLista();

        for (JSONObject ristorante : results) {
            String nome = ristorante.getString("name");
            String indirizzo = ristorante.optString("vicinity", "Indirizzo non disponibile");
            double lat = 0;
            double lng = 0;
            if (ristorante.has("geometry")) {
                JSONObject location = ristorante.getJSONObject("geometry").getJSONObject("location");
                lat = location.getDouble("lat");
                lng = location.getDouble("lng");
            }
            listaRistoranti.aggiungiRistorante(RestaurantFactory.creaRistorante(nome, indirizzo, lat, lng));
        }

        return listaRistoranti.getBeansList();
    }
}
