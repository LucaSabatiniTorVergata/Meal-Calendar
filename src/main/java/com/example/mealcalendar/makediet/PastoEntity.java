package com.example.mealcalendar.makediet;



public class PastoEntity {
    private String nome;
    private double kcal;

    public PastoEntity(String nome, double kcal) {
        this.nome = nome;
        this.kcal = kcal;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getKcal() {
        return kcal;
    }

    public void setKcal(double kcal) {
        this.kcal = kcal;
    }
}
