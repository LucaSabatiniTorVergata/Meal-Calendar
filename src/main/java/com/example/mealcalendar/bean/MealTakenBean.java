package com.example.mealcalendar.bean;

public class MealTakenBean {
    private String nome;
    private String descrizione;
    private int kcal;

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Il nome del pasto non può essere null o vuoto.");
        }
        this.nome = nome;
    }

    public void setDescrizione(String descrizione) {
        if (descrizione == null) {
            throw new IllegalArgumentException("La descrizione non può essere null.");
        }
        this.descrizione = descrizione;
    }

    public void setKcal(int kcal) {
        if (kcal < 0) {
            throw new IllegalArgumentException("Le kcal non possono essere negative.");
        }
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
