package com.example.mealcalendar.model;

public class MealEntity {

    private String nome;
    private String descrizione;
    private int kcal;

    public MealEntity(String nome, String descrizione, int kcal) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.kcal = kcal;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public int getKcal() {
        return kcal;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }
}


