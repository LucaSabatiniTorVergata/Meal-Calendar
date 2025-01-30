package com.example.mealcalendar;

import com.example.mealcalendar.FiltersRestaurantBean;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;


public class ChooseRestaurantController {

    private static final String API_KEY = "INSERISCI_LA_TUA_API_KEY";

    public void TrovaRistorante(FiltersRestaurantBean Filtro) throws IOException {

        String UrlString = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                "location=41.9028,12.4964" +  // 📍 Posizione fissa (Roma) → Puoi sostituirlo con la posizione dell’utente
                "&radius=" + (Filtro.getDistanza() * 1000) +  // 🔁 Convertiamo km in metri
                "&type=restaurant" +
                "&keyword=" + Filtro.getTipoDieta() + "+" + Filtro.getPasto() +  // 🏷️ Filtri della ricerca
                "&key=" + API_KEY;

        URL Url = new URL(UrlString);
        HttpURLConnection conn = (HttpURLConnection) Url.openConnection();
        conn.setRequestMethod("GET");

        Scanner scanner = new Scanner(Url.openStream());
        StringBuilder response = new StringBuilder();
        while (scanner.hasNext()) {
            response.append(scanner.nextLine());
        }
        scanner.close();

        JSONObject jsonResponse = new JSONObject(response.toString());
        JSONArray results = jsonResponse.getJSONArray("results");

        if (results.length() > 0) {
            System.out.println("🍽️ Ristoranti trovati:");
            for (int i = 0; i < results.length(); i++) {
                JSONObject ristorante = results.getJSONObject(i);
                String nome = ristorante.getString("name");
                String indirizzo = ristorante.optString("vicinity", "Indirizzo non disponibile");
                System.out.println((i + 1) + ". " + nome + " - 📍 " + indirizzo);
            }
        } else {
            System.out.println("❌ Nessun ristorante trovato con questi filtri!");
        }
    }


}
