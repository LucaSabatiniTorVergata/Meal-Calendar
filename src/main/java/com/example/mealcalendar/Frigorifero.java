package com.example.mealcalendar;

import java.util.*;

public class Frigorifero {
    private Map<String, Integer> inventario;

    public Frigorifero() {
        this.inventario = new HashMap<>();
    }

    public void aggiungiIngrediente(String nome, int quantita) {
        inventario.put(nome, inventario.getOrDefault(nome, 0) + quantita);
        stampaInventario();
    }

    public Map<String, Integer> getInventario() {
        return inventario;
    }

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
