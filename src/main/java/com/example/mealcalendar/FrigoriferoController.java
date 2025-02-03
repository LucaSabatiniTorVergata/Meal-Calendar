package com.example.mealcalendar;

import java.util.Map;

public class FrigoriferoController {
    // Creazione dell'istanza Singleton
    private static FrigoriferoController instance;

    private IngredienteSltpat ingredienteSingleton;

    // Costruttore privato per evitare che vengano create altre istanze
    public FrigoriferoController() {
        this.ingredienteSingleton = IngredienteSltpat.getInstance(); // Otteniamo l'istanza unica della lista
    }

    // Metodo per ottenere l'istanza Singleton


    // Aggiunge un ingrediente alla lista
    public void aggiungiIngrediente(String nomeIngrediente, int quantita) {
        ingredienteSingleton.aggiungiIngrediente(nomeIngrediente, quantita);
    }

    // Ottiene l'inventario
    public Map<String, Integer> getInventario() {
        return ingredienteSingleton.getInventario();
    }

    // Stampa l'inventario
    public void stampaInventario() {
        ingredienteSingleton.stampaInventario();
    }
}
