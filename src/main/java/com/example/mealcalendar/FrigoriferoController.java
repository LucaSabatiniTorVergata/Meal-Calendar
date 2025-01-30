package com.example.mealcalendar;

import java.util.List;

public class FrigoriferoController {

    private Frigorifero frigorifero;

    public FrigoriferoController() {
        frigorifero = new Frigorifero();  // Istanzia il frigorifero vuoto
    }

    public void aggiungiIngrediente(String nomeIngrediente) {
        Ingrediente ingrediente = new Ingrediente(nomeIngrediente);
        frigorifero.aggiungiIngrediente(ingrediente);
    }

    public List<Ingrediente> getInventario() {
        return frigorifero.getInventario();
    }
    public void stampaInventario() {
        frigorifero.stampaInventario();
    }
}
