package com.example.mealcalendar;

public class FiltersRestaurantBean {

    private String tipoDieta;
    private String pasto;
    private double distanza;



    public FiltersRestaurantBean(String TipoDieta, String Pasto, double Distanza) {
        this.tipoDieta = TipoDieta;
        this.pasto = Pasto;
        this.distanza = Distanza;
    }

    public String getTipoDieta() {
        return tipoDieta;
    }

    public String getPasto() {
        return pasto;
    }

    public double getDistanza() {
        return distanza;
    }
}