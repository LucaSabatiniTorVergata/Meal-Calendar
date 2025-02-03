package com.example.mealcalendar;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;


//coordinate di roma: 41.9028,12.4964
public class ChooseRestaurantController {


    private static final String API_KEY = "AIzaSyDX5EA-t9Bpsmm8VIrhCT9eCgpX-9v5PCE";
    private FiltersRestaurantBean filtro;

    public ChooseRestaurantController(FiltersRestaurantBean Filtro) {
        this.filtro=Filtro;
    }

    public List<ReturnRestaurantsBean> trovaRistorante() throws IOException {

        //Costruzione della query API
        String urlString = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                "location=41.9028,12.4964" +  // 📍 Posizione fissa (Roma) → Puoi sostituirlo con la posizione dell’utente
                "&radius=" + (filtro.getDistanza() * 1000) +  // 🔁 Convertiamo km in metri
                "&type=restaurant" +
                "&keyword=" + filtro.getTipoDieta() + "+" + filtro.getPasto() +  // 🏷️ Filtri della ricerca
                "&key=" + API_KEY;
        //Costruzione della query API
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        //Lettura della risposta API
        Scanner scanner = new Scanner(url.openStream());
        StringBuilder response = new StringBuilder();
        while (scanner.hasNext()) {
            response.append(scanner.nextLine());
        }
        scanner.close();
        //Parsing della risposta JSON
        JSONObject jsonResponse = new JSONObject(response.toString());
        JSONArray results = jsonResponse.getJSONArray("results");

        RestaurantListSLTPat listaRistoranti = RestaurantListSLTPat.getInstance();
        listaRistoranti.svuotaLista();// Pulisce la lista prima di aggiungere nuovi ristoranti

        List<ReturnRestaurantsBean> ristorantibeans = new ArrayList<>();

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
                RestaurantEntity nuovoristorante = RestaurantFactory.creaRistorante(nome, indirizzo, lat, lng);
                listaRistoranti.aggiungiRistorante(nuovoristorante);
            }
        }
        ristorantibeans=listaRistoranti.getBeansList();
        return ristorantibeans;
    }
}
