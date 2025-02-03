package com.example.mealcalendar;

import java.util.Map;

public class FrigoriferoController {
    // Creazione dell'istanza Singleton
    private static FrigoriferoController instance;

    private IngredienteSltpat ingredienteSingleton;

    // Costruttore privato per evitare che vengano create altre istanze
    private FrigoriferoController() {
        this.ingredienteSingleton = IngredienteSltpat.getInstance(); // Otteniamo l'istanza unica della lista
    }

    // Metodo per ottenere l'istanza Singleton
    public static FrigoriferoController getInstance() {
        if (instance == null) {
            instance = new FrigoriferoController(); // Crea l'istanza solo se non esiste già
        }
        return instance;
    }

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
