package com.example.mealcalendar.model;

import java.util.List;

public class RistoranteEntity {

    private String nome;
    private String indirizzo;
    private int postiDisponibili;
    private List<String> menu;
    private List<PrenotazioneEntity> prenotazioniAttive;
    private TipologiaRistorante tipologiaRistorante;


    public String getNome() {
        return nome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public int getPostiDisponibili() {
        return postiDisponibili;
    }

    public List<String> getMenu() {
        return menu;
    }

    public List<PrenotazioneEntity> getPrenotazioniAttive() {
        return prenotazioniAttive;
    }

    public TipologiaRistorante getTipologiaRistorante() {
        return tipologiaRistorante;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public void setPostiDisponibili(int postiDisponibili) {
        this.postiDisponibili = postiDisponibili;
    }

    public void setMenu(List<String> menu) {
        this.menu = menu;
    }

    public void setPrenotazioniAttive(List<PrenotazioneEntity> prenotazioniAttive) {
        this.prenotazioniAttive = prenotazioniAttive;
    }

    public void setTipologiaRistorante(TipologiaRistorante tipologiaRistorante) {
        this.tipologiaRistorante = tipologiaRistorante;
    }
}
