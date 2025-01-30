package com.example.mealcalendar;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;



public class ChooseRestaurantController {

    private static final String API_KEY = "AIzaSyDX5EA-t9Bpsmm8VIrhCT9eCgpX-9v5PCE";

    public List<ReturnRestaurantsBean> TrovaRistorante(FiltersRestaurantBean Filtro) throws IOException {

        //Costruzione della query API
        String UrlString = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                "location=41.9028,12.4964" +  // 📍 Posizione fissa (Roma) → Puoi sostituirlo con la posizione dell’utente
                "&radius=" + (Filtro.getDistanza() * 1000) +  // 🔁 Convertiamo km in metri
                "&type=restaurant" +
                "&keyword=" + Filtro.getTipoDieta() + "+" + Filtro.getPasto() +  // 🏷️ Filtri della ricerca
                "&key=" + API_KEY;
        //Costruzione della query API
        URL Url = new URL(UrlString);
        HttpURLConnection conn = (HttpURLConnection) Url.openConnection();
        conn.setRequestMethod("GET");
        //Lettura della risposta API
        Scanner scanner = new Scanner(Url.openStream());
        StringBuilder response = new StringBuilder();
        while (scanner.hasNext()) {
            response.append(scanner.nextLine());
        }
        scanner.close();
        //Parsing della risposta JSON
        JSONObject jsonResponse = new JSONObject(response.toString());
        JSONArray results = jsonResponse.getJSONArray("results");
        List<ReturnRestaurantsBean> listaRistoranti = new ArrayList<>();
        if (results.length() > 0) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject ristorante = results.getJSONObject(i);
                String nome = ristorante.getString("name"); // Nome ristorante
                String indirizzo = ristorante.optString("vicinity", "Indirizzo non disponibile");

                //Recuperiamo le coordinate GPS (latitudine e longitudine)
                double lat = 0, lng = 0;
                if (ristorante.has("geometry")) {
                    JSONObject location = ristorante.getJSONObject("geometry").getJSONObject("location");
                    lat = location.getDouble("lat");
                    lng = location.getDouble("lng");
                }

                //  Creiamo un oggetto Bean e lo aggiungiamo alla lista
                ReturnRestaurantsBean bean = new ReturnRestaurantsBean(nome, indirizzo, lat, lng);
                listaRistoranti.add(bean);
            }
        }
        return listaRistoranti;
    }
}
