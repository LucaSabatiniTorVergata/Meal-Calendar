package com.example.mealcalendar;

public class FiltersRestaurantBean {

    private String TipoDieta;
    private String Pasto;
    private double Distanza;

    public FiltersRestaurantBean(String TipoDieta, String Pasto, double Distanza) {
        this.TipoDieta = TipoDieta;
        this.Pasto = Pasto;
        this.Distanza = Distanza;
    }

    public String getTipoDieta() {
        return TipoDieta;
    }

    public String getPasto() {
        return Pasto;
    }

    public double getDistanza() {
        return Distanza;
    }
}