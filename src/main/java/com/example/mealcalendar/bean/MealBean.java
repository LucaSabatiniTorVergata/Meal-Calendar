package com.example.mealcalendar.bean;

public class MealBean {

    private String nome;
    private String descrizione;
    private int kcal;

    public void setNome(String n) {
        if (n == null || n.trim().isEmpty()) {
            throw new IllegalArgumentException("Il nome del pasto non può essere vuoto.");
        }
        this.nome = n.trim();
    }

    public void setDescrizione(String d) {

        if (d == null ) {
            throw new IllegalArgumentException("La descrizione non può essere una stringa vuota.");
        }
        this.descrizione = d;
    }

    public void setKcal(int k) {
        if (k < 0) {
            throw new IllegalArgumentException("Le kcal non possono essere negative.");
        }
        this.kcal = k;
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
