package com.example.mealcalendar;

public class RestaurantFactory {

    private RestaurantFactory () {
    }

    public static RestaurantEntity CreaRistorante(String Nome, String Indirizzo, double Lat, double Lng) {
        return new RestaurantEntity(Nome, Indirizzo, Lat, Lng);
    }
}

