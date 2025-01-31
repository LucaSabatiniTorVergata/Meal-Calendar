package com.example.mealcalendar;

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

    public void incrementaQuantita(int quantita) {
        this.quantita+=quantita;
    }
    @Override
    public String toString() {
        return nome + " (Quantità: " + quantita + ")";
    }
}

