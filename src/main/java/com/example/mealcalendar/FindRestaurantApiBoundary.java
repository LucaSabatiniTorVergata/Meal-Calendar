package com.example.mealcalendar;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class FindRestaurantApiBoundary {
    private static final String API_KEY = System.getenv("GOOGLE_API_KEY");

    public List<JSONObject> cercaRistoranti(FiltersRestaurantBean filtro) throws IOException {
        String urlString = buildUrl(filtro);
        String jsonResponse = callGooglePlacesApi(urlString);
        return parseResults(jsonResponse);
    }

    private String buildUrl(FiltersRestaurantBean filtro) {
        return "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                "location=41.9028,12.4964" +
                "&radius=" + (filtro.getDistanza() * 1000) +
                "&type=restaurant" +
                "&keyword=" + filtro.getTipoDieta() + "+" + filtro.getPasto() +
                "&key=" + API_KEY;
    }

    private String callGooglePlacesApi(String urlString) throws IOException {
        URL url = new URL(urlString);
        try (Scanner scanner = new Scanner(url.openStream())) {
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            return response.toString();
        }
    }

    private List<JSONObject> parseResults(String jsonResponse) {
        JSONObject json = new JSONObject(jsonResponse);
        JSONArray results = json.getJSONArray("results");
        List<JSONObject> ristoranti = new ArrayList<>();
        for (int i = 0; i < results.length(); i++) {
            ristoranti.add(results.getJSONObject(i));
        }
        return ristoranti;
    }
}
