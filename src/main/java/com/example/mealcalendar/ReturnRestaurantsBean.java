package com.example.mealcalendar;

public class ReturnRestaurantsBean {

    private String NomeRistoranteTrovato;
    private String IndirizzoRistoranteTrovato;
    private double Latitudine;
    private double Longitudine;

    //costruttore
    public ReturnRestaurantsBean(String Nome, String Indirizzo, double Latitudine, double Longitudine) {
        this.NomeRistoranteTrovato = Nome;
        this.IndirizzoRistoranteTrovato = Indirizzo;
        this.Latitudine = Latitudine;
        this.Longitudine = Longitudine;
    }


    public String getNome() {
        return NomeRistoranteTrovato;
    }

    public void setNome(String nome) {
        this.NomeRistoranteTrovato = nome;
    }

    public String getIndirizzo() {
        return IndirizzoRistoranteTrovato;
    }

    public void setIndirizzo(String indirizzo) {
        this.IndirizzoRistoranteTrovato = indirizzo;
    }

    public double getLatitudine() {
        return Latitudine;
    }

    public void setLatitudine(double latitudine) {
        this.Latitudine = latitudine;
    }

    public double getLongitudine() {
        return Longitudine;
    }

    public void setLongitudine(double longitudine) {
        this.Longitudine = longitudine;
    }

    @Override
    public String toString() {
        return "🍽️ " + NomeRistoranteTrovato + " - 📍 " + IndirizzoRistoranteTrovato;
    }
}
