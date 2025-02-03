package com.example.mealcalendar;

public class ReturnRestaurantsBean {

    private String nomeRistoranteTrovato;
    private String indirizzoRistoranteTrovato;
    private double latitudine;
    private double longitudine;

    //costruttore
    public ReturnRestaurantsBean(String nome, String indirizzo, double latitudine, double longitudine) {
        this.nomeRistoranteTrovato = nome;
        this.indirizzoRistoranteTrovato = indirizzo;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }


    public String getNome() {
        return nomeRistoranteTrovato;
    }

    public void setNome(String nome) {
        this.nomeRistoranteTrovato = nome;
    }

    public String getIndirizzo() {
        return indirizzoRistoranteTrovato;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzoRistoranteTrovato = indirizzo;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }

    @Override
    public String toString() {
        return "🍽️ " + nomeRistoranteTrovato + " - 📍 " + indirizzoRistoranteTrovato;
    }
}
