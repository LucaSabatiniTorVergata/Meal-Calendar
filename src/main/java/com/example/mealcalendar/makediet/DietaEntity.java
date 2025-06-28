package com.example.mealcalendar.makediet;

import com.example.mealcalendar.login.UserEntity;

import java.time.LocalDate;
import java.util.List;

public class DietaEntity {

    private String nome;
    private String descrizione;
    private String autore;
    private List<GiornoDietaEntity> giorni;
    private String dataPubblicazione;
    private int durataSettimane;

    public DietaEntity(String nome, String descrizione, String autore,
                                                   List<GiornoDietaEntity> giorni, String dataPubblicazione,
                                                   int durataSettimane) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.autore = autore;
        this.giorni = giorni;
        this.dataPubblicazione = dataPubblicazione;
        this.durataSettimane = durataSettimane;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String  getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public List<GiornoDietaEntity> getGiorni() {
        return giorni;
    }

    public void setGiorni(List<GiornoDietaEntity> giorni) {
        this.giorni = giorni;
    }

    public String getDataPubblicazione() {
        return dataPubblicazione;
    }

    public void setDataPubblicazione(String dataPubblicazione) {
        this.dataPubblicazione = dataPubblicazione;
    }

    public int getDurataSettimane() {
        return durataSettimane;
    }

    public void setDurataSettimane(int durataSettimane) {
        this.durataSettimane = durataSettimane;
    }
}
