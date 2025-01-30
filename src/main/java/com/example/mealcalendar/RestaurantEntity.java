package com.example.mealcalendar;


public class RestaurantEntity {

    private String Nome;
    private String Indirizzo;
    private double Latitudine;
    private double Longitudine;

    public RestaurantEntity(String nome, String indirizzo, double latitudine, double longitudine) {
        this.Nome = nome;
        this.Indirizzo = indirizzo;
        this.Latitudine = latitudine;
        this.Longitudine = longitudine;
    }
    public String getnome() {
        return Nome;
    }

    public String getindirizzo() {
        return Indirizzo;
    }

    public double getlatitudine() {
        return Latitudine;
    }

    public double getlongitudine() {
        return Longitudine;
    }

    @Override
    public String toString() {
        return "🍽️ " + Nome + " - 📍 " + Indirizzo;
    }



}