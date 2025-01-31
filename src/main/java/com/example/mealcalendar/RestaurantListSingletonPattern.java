package com.example.mealcalendar;
import java.util.Vector;
import java.util.ArrayList;
import java.util.List;

public class RestaurantListSingletonPattern {

    private static RestaurantListSingletonPattern instance = null;
    private List<RestaurantEntity> ListaRistoranti;

    private RestaurantListSingletonPattern() {
        ListaRistoranti = new ArrayList<>();
    }

    public static RestaurantListSingletonPattern getInstance () {
        if (instance == null) {
            instance = new RestaurantListSingletonPattern();
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