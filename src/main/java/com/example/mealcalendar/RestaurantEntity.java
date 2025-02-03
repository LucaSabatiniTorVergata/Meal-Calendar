package com.example.mealcalendar;


public class RestaurantEntity {

    private String nome;
    private String indirizzo;
    private double latitudine;
    private double longitudine;

    public RestaurantEntity(String nome, String indirizzo, double latitudine, double longitudine) {
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }
    public String getnome() {
        return nome;
    }

    public String getindirizzo() {
        return indirizzo;
    }

    public double getlatitudine() {
        return latitudine;
    }

    public double getlongitudine() {
        return longitudine;
    }

    @Override
    public String toString() {
        return "🍽️ " + nome + " - 📍 " + indirizzo;
    }



}