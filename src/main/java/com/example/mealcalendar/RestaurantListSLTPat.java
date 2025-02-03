package com.example.mealcalendar;
import java.util.ArrayList;
import java.util.List;

public class RestaurantListSLTPat {

    private static RestaurantListSLTPat instance = null;
    private List<RestaurantEntity> listaRistoranti;

    private RestaurantListSLTPat() {
        listaRistoranti = new ArrayList<>();
    }

    public static RestaurantListSLTPat getInstance () {
        if (instance == null) {
            instance = new RestaurantListSLTPat();
        }
        return instance;
    }

    public void aggiungiRistorante(RestaurantEntity ristorante) {
        listaRistoranti.add(ristorante);
    }

    public List<RestaurantEntity> getRistoranti() {
        return listaRistoranti;
    }

    public void svuotaLista() {
        listaRistoranti.clear();
    }

    public List<ReturnRestaurantsBean> getBeansList() {
        List<ReturnRestaurantsBean> beansList = new ArrayList<>();
        for (RestaurantEntity ristorante : listaRistoranti) {
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