package com.example.mealcalendar;

public class FiltersRestaurantBean {

    private String tipoDieta;
    private String pasto;
    private double distanza;

   public FiltersRestaurantBean() {}

    public FiltersRestaurantBean(String tipoDieta, String pasto, double distanza) {
        this.tipoDieta = tipoDieta;
        this.pasto = pasto;
        this.distanza = distanza;
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

    public void setTipoDieta(String tipoDieta) {
       this.tipoDieta = tipoDieta;
    }
    public void setPasto(String pasto) {
       this.pasto = pasto;
    }
    public void setDistanza(double distanza) {
       this.distanza = distanza;
    }

}