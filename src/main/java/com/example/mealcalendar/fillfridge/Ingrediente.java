package com.example.mealcalendar.fillfridge;

public class Ingrediente {
    private String nome;
    private int quantita;

    public Ingrediente(String nome, int quantita) {
        this.nome = nome;
        this.quantita = quantita;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantita() {
        return quantita;
    }

    public void aggiungiQuantita(int quantita) {
        this.quantita += quantita;
    }
}
