package com.example.mealcalendar.model;


public class RistoranteEntity {

    private String nome;
    private String indirizzo;
    private int postiDisponibili;
    private TipologiaRistorante tipologiaRistorante;


    public String getNome() { return nome; }
    public String getIndirizzo() { return indirizzo; }
    public int getPostiDisponibili() { return postiDisponibili; }
    public TipologiaRistorante getTipologiaRistorante() { return tipologiaRistorante; }

    public void setNome(String nome) { this.nome = nome; }
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }
    public void setPostiDisponibili(int postiDisponibili) { this.postiDisponibili = postiDisponibili; }
    public void setTipologiaRistorante(TipologiaRistorante tipologiaRistorante) { this.tipologiaRistorante = tipologiaRistorante; }
}
