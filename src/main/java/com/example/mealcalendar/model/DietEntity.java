package com.example.mealcalendar.model;



import java.util.ArrayList;
import java.util.List;

public class DietEntity {

    private String nome;
    private String descrizione;
    private int durata;
    private String nutritionistUsername;
    private List<DayEntity> giorni = new ArrayList<>();

    public DietEntity(String nome, String descrizione, int durata, String nutritionistUsername) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.durata = durata;
        this.nutritionistUsername = nutritionistUsername;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public int getDurata() {
        return durata;
    }

    public String getNutritionistUsername() {
        return nutritionistUsername;
    }

    public List<DayEntity> getGiorni() {
        return giorni;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public void setNutritionistUsername(String nutritionistUsername) {
        this.nutritionistUsername = nutritionistUsername;
    }

    public void setGiorni(List<DayEntity> giorni) {
        this.giorni = giorni;
    }

    public void addDay(DayEntity giorno) {
        if (giorno != null) {
            giorni.add(giorno);
        }
    }
}
