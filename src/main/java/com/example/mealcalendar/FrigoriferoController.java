package com.example.mealcalendar;


import java.util.Map;

public class FrigoriferoController {
    private Frigorifero frigorifero;

    public FrigoriferoController() {
        this.frigorifero = new Frigorifero();
    }

    public void gestisciIngrediente(String nome) {
        frigorifero.aggiungiIngrediente(nome);
    }

    public Map<String, Integer> getInventario() {
        return frigorifero.getInventario();
    }
}
