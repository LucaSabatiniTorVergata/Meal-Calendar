package com.example.mealcalendar;

import java.util.*;

public class IngredienteSingleton {
    // Creazione dell'istanza Singleton
    private static IngredienteSingleton instance;

    // La lista degli ingredienti è rappresentata da una mappa di nome e quantità
    private Map<String, Integer> inventario;

    // Costruttore privato per evitare che vengano create altre istanze
    private IngredienteSingleton() {
        this.inventario = new LinkedHashMap<>();
    }

    // Metodo per ottenere l'istanza Singleton
    public static IngredienteSingleton getInstance() {
        if (instance == null) {
            instance = new IngredienteSingleton(); // Crea l'istanza solo se non esiste già
        }
        return instance;
    }

    // Metodo per aggiungere un ingrediente alla lista
    public void aggiungiIngrediente(String nome, int quantita) {
        if (inventario.containsKey(nome)) {
            inventario.put(nome, inventario.get(nome) + quantita);
        } else {
            // Inseriamo l'ingrediente come primo
            Map<String, Integer> nuovoInventario = new LinkedHashMap<>();
            nuovoInventario.put(nome, quantita);
            nuovoInventario.putAll(inventario); // Aggiungiamo il vecchio inventario
            inventario = nuovoInventario;
        }
    }

    // Metodo per ottenere l'inventario (mappa degli ingredienti)
    public Map<String, Integer> getInventario() {
        return inventario;
    }

    // Metodo per stampare l'inventario
    public void stampaInventario() {
        System.out.println("🧊 Contenuto del Frigorifero:");
        if (inventario.isEmpty()) {
            System.out.println("⚠️ Il frigorifero è vuoto!");
        } else {
            for (Map.Entry<String, Integer> entry : inventario.entrySet()) {
                System.out.println("- " + entry.getKey() + " | Quantità: " + entry.getValue());
            }
        }
    }
}
