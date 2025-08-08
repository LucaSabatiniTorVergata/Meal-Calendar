package com.example.mealcalendar.model;

public class TakenMealEntity {
    private String nome;
    private String descrizione;
    private int kcal;

    public TakenMealEntity(String nome, String descrizione, int kcal) {
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
}
