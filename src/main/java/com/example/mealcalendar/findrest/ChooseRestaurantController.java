package com.example.mealcalendar.findrest;


import java.io.IOException;


import java.util.ArrayList;
import java.util.List;

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
        List<RestaurantEntity>listaRistoranti=new ArrayList<>();

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
            listaRistoranti.add(RestaurantFactory.creaRistorante(nome, indirizzo, lat, lng));
        }
        return getBeansList(listaRistoranti);
    }

    public List<ReturnRestaurantsBean> getBeansList(List<RestaurantEntity>lista) {
        List<ReturnRestaurantsBean> beansList = new ArrayList<>();
        for (RestaurantEntity ristorante : lista) {
            beansList.add(new ReturnRestaurantsBean(
                    ristorante.getnome(),
                    ristorante.getindirizzo(),
                    ristorante.getlatitudine(),
                    ristorante.getlongitudine()
            ));
        }
        return beansList;
    }
}
