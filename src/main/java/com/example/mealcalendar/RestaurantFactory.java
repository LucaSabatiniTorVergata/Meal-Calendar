package com.example.mealcalendar;

public class RestaurantFactory {

    private RestaurantFactory () {
    }

    public static RestaurantEntity creaRistorante(String nome, String indirizzo, double lat, double lng) {
        return new RestaurantEntity(nome, indirizzo, lat, lng);
    }
}

