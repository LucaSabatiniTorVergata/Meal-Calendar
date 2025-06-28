package com.example.mealcalendar.makediet;

import com.example.mealcalendar.login.UserEntity;

import java.time.LocalDate;
import java.util.List;

public class DietaBean {

    private String nome;
    private String descrizione;
    private List<GiornoBean> giorni;
    private String autore;
    private String dataPubblicazione;
    private int durata;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {return nome;}

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;

    }
    public String getDescrizione() {return descrizione;}

    public void setGiorni(List<GiornoBean> giorni) { this.giorni = giorni;}
    public List<GiornoBean> getGiorni() {return giorni;}

    public void setAutore(String autore) {this.autore = autore;}
    public String getAutore() {return autore;}
    public void setDataPubblicazione(String dataPubblicazione) {
        this.dataPubblicazione = dataPubblicazione;
    }
    public String getDataPubblicazione() {return dataPubblicazione;}
   public void setDurata(int durata) {this.durata = durata;}
    public int getDurata() {return durata;}
}
