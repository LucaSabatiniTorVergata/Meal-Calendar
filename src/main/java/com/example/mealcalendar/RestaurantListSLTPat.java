package com.example.mealcalendar;
import java.util.ArrayList;
import java.util.List;

public class RestaurantListSLTPat {

    private static RestaurantListSLTPat instance = null;
    private List<RestaurantEntity> ListaRistoranti;

    private RestaurantListSLTPat() {
        ListaRistoranti = new ArrayList<>();
    }

    public static RestaurantListSLTPat getInstance () {
        if (instance == null) {
            instance = new RestaurantListSLTPat();
        }
        return instance;
    }

    public void AggiungiRistorante(RestaurantEntity ristorante) {
        ListaRistoranti.add(ristorante);
    }

    public List<RestaurantEntity> GetRistoranti() {
        return ListaRistoranti;
    }

    public void svuotaLista() {
        ListaRistoranti.clear();
    }

    public List<ReturnRestaurantsBean> GetBeansList() {
        List<ReturnRestaurantsBean> beansList = new ArrayList<>();
        for (RestaurantEntity ristorante : ListaRistoranti) {
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