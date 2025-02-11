package com.example.mealcalendar;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

public class InventarioDao {

    private static final String FILE_PATH = "inventario.txt";
    private static InventarioDao instance = null;
    private boolean usePersistence; // Se true usa il file, altrimenti RAM
    private Map<String, Integer> inventario;
    private Logger logger = Logger.getLogger(getClass().getName());

    // Costruttore privato per Singleton
    private InventarioDao(boolean usePersistence) throws IOException {
        this.usePersistence = usePersistence;
        if (usePersistence) {
            this.inventario = loadInventario();
        } else {
            this.inventario = new LinkedHashMap<>();
        }
    }

    // Metodo per ottenere l'istanza Singleton
    public static InventarioDao getInstance(boolean usePersistence) throws IOException {
        if (instance == null) {
            instance = new InventarioDao(usePersistence);
        }
        return instance;
    }

    // Metodo per aggiungere un ingrediente
    public void aggiungiIngrediente(String nome, int quantita) throws IOException {
        if (inventario.containsKey(nome)) {
            inventario.put(nome, inventario.get(nome) + quantita);
        } else {
            inventario.put(nome, quantita);
        }
        if (usePersistence) saveInventario();
    }

    // Metodo per rimuovere un ingrediente
    public void rimuoviIngrediente(String nome, int quantita) throws IOException {
        if (inventario.containsKey(nome)) {
            int current = inventario.get(nome);
            if (quantita >= current) {
                inventario.remove(nome);
                logger.info("Rimosso completamente: " + nome);
            } else {
                inventario.put(nome, current - quantita);
                logger.info("Ridotta quantità di " + nome + " a " + (current - quantita));
            }
        } else {
            logger.info("Ingrediente non trovato: " + nome);
        }
        if (usePersistence) saveInventario();
    }

    // Metodo per ottenere l'inventario
    public Map<String, Integer> getInventario() {
        return new LinkedHashMap<>(inventario);
    }

    // Metodo per stampare l'inventario
    public void stampaInventario() {
        logger.info("🧊 Contenuto dell'inventario:");
        if (inventario.isEmpty()) {
            logger.info("⚠️ Il frigorifero è vuoto!");
        } else {
            for (Map.Entry<String, Integer> entry : inventario.entrySet()) {
                logger.info("- " + entry.getKey() + " | Quantità: " + entry.getValue());
            }
        }
    }

    // Carica i dati dal file
    private Map<String, Integer> loadInventario() throws IOException {
        Map<String, Integer> map = new LinkedHashMap<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            file.createNewFile();
            return map;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String nome = parts[0].trim();
                    int quantita = Integer.parseInt(parts[1].trim());
                    map.put(nome, quantita);
                }
            }
        }
        return map;
    }

    // Salva i dati su file
    private void saveInventario() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, Integer> entry : inventario.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        }
    }
}
