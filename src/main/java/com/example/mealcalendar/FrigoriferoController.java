package com.example.mealcalendar;

import java.util.List;

public class FrigoriferoController {
    private Frigorifero frigorifero;

    public FrigoriferoController() {
        frigorifero = new Frigorifero();  // Crea un frigorifero vuoto
    }

    public void aggiungiIngrediente(String nomeIngrediente, int quantita) {
        Ingrediente ingrediente = new Ingrediente(nomeIngrediente, quantita);  // Passa la quantità al costruttore
        frigorifero.aggiungiIngrediente(ingrediente);  // Aggiungi l'ingrediente al frigorifero
    }

    public List<Ingrediente> getInventario() {
        return frigorifero.getInventario();
    }

    // Metodo per stampare l'inventario nella console
    public void stampaInventario() {
        frigorifero.stampaInventario();
    }
}


