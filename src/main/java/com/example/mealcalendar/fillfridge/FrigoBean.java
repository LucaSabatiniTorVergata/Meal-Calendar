package com.example.mealcalendar.fillfridge;

public class FrigoBean {
    private String nomeIngrediente;
    private int quantita;

    // Costruttore
    public FrigoBean(String nomeIngrediente, int quantita) {
        this.nomeIngrediente = nomeIngrediente;
        this.quantita = quantita;
    }

    // Getters e Setters
    public String getNomeIngrediente() {
        return nomeIngrediente;
    }

    public void setNomeIngrediente(String nomeIngrediente) {
        this.nomeIngrediente = nomeIngrediente;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
}
