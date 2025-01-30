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

    //metodi per ottenere il ristorante
    public String getNome() {
        return NomeRistoranteTrovato;
    }

    public String getIndirizzo() {
        return IndirizzoRistoranteTrovato;
    }

    public double getLatitudine() {
        return Latitudine;
    }

    public double getLongitudine() {
        return Longitudine;
    }

    @Override
    public String toString() {
        return NomeRistoranteTrovato + " - 📍 " + IndirizzoRistoranteTrovato;
    }
}
