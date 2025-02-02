package com.example.mealcalendar;

import java.util.*;
import java.util.logging.Logger;


public class Frigorifero {
    private Map<String, Integer> inventario;


    Logger logger = Logger.getLogger(getClass().getName());

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
        logger.info("🧊 Contenuto del Frigorifero:");
        if (inventario.isEmpty()) {
            logger.info("⚠️ Il frigorifero è vuoto!");
        } else {
            for (Map.Entry<String, Integer> entry : inventario.entrySet()) {
                logger.info("- " + entry.getKey() + " | Quantità: " + entry.getValue());
            }
        }
    }
}
