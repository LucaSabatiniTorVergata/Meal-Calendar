package com.example.mealcalendar;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IngredienteSltpat {

    // Logger dichiarato come statico e finale
    private static final Logger logger = Logger.getLogger(IngredienteSltpat.class.getName());

    // Creazione dell'istanza Singleton
    private static IngredienteSltpat instance = null;

    // La lista degli ingredienti è rappresentata da una mappa di nome e quantità
    private Map<String, Integer> inventario;

    // Costruttore privato per evitare che vengano create altre istanze
    private IngredienteSltpat() {
        this.inventario = new LinkedHashMap<>();
    }

    // Metodo per ottenere l'istanza Singleton
    public static IngredienteSltpat getInstance() {
        if (instance == null) {
            instance = new IngredienteSltpat(); // Crea l'istanza solo se non esiste già
        }
        return instance;
    }

    // Metodo per aggiungere un ingrediente alla lista
    public void aggiungiIngrediente(String nome, int quantita) {
        if (inventario.containsKey(nome)) {
            inventario.put(nome, inventario.get(nome) + quantita);
            logger.log(Level.INFO, "Aggiunta quantità di {0}: {1}", new Object[]{nome, quantita});
        } else {
            // Inseriamo l'ingrediente come primo
            Map<String, Integer> nuovoInventario = new LinkedHashMap<>();
            nuovoInventario.put(nome, quantita);
            nuovoInventario.putAll(inventario); // Aggiungiamo il vecchio inventario
            inventario = nuovoInventario;
            logger.log(Level.INFO, "Aggiunto nuovo ingrediente: {0} con quantità: {1}", new Object[]{nome, quantita});
        }
    }

    // Metodo per rimuovere un ingrediente dalla lista
    public void rimuoviIngrediente(String nome, int quantita) {
        if (inventario.containsKey(nome)) {
            int currentQuantity = inventario.get(nome);
            if (quantita >= currentQuantity) {
                // Se la quantità da rimuovere è maggiore o uguale a quella presente, elimina l'ingrediente
                inventario.remove(nome);
                logger.log(Level.INFO, "Rimosso completamente l''ingrediente: {0}", nome);
            } else {
                // Altrimenti, diminuisci la quantità
                inventario.put(nome, currentQuantity - quantita);
                logger.log(Level.INFO, "Diminuita la quantità di {0} di {1}. Quantità rimanente: {2}",
                        new Object[]{nome, quantita, currentQuantity - quantita});
            }
        } else {
            // Il singolo apostrofo viene duplicato per essere interpretato come carattere letterale
            logger.log(Level.WARNING, "L''ingrediente {0} non esiste nell''inventario.", nome);
        }
    }

    // Metodo per ottenere l'inventario (mappa degli ingredienti)
    public Map<String, Integer> getInventario() {
        return inventario;
    }

    // Metodo per stampare l'inventario
    public void stampaInventario() {
        logger.info("🧊 Contenuto del Frigorifero:");
        if (inventario.isEmpty()) {
            logger.info("⚠️ Il frigorifero è vuoto!");
        } else {
            for (Map.Entry<String, Integer> entry : inventario.entrySet()) {
                logger.log(Level.INFO, "- {0} | Quantità: {1}", new Object[]{entry.getKey(), entry.getValue()});
            }
        }
    }
}
