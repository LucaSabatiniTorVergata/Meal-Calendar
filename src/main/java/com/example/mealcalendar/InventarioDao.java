package com.example.mealcalendar;


import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InventarioDao implements InventarioDaoInterface {

    private static final Logger LOGGER = Logger.getLogger(InventarioDao.class.getName());
    private static InventarioDao instance;
    private Map<String, Integer> inventario;
    private static final String FILE_PATH = "inventario.txt";
    private boolean useDemo=false;

    private InventarioDao(boolean useDemo) {
        LOGGER.log(Level.INFO, "InventarioDao: Inizializzazione con persistenza {0}", useDemo);
        this.useDemo= useDemo;
        this.inventario = new HashMap<>();
        if (!useDemo) {
            loadInventario();
        }
    }

    public static InventarioDao getInstance(boolean usePersistence) {
        if (instance == null) {
            LOGGER.log(Level.INFO, "InventarioDao: Creazione della nuova istanza con persistenza {0}", usePersistence);
            instance = new InventarioDao(usePersistence);
        }
        return instance;
    }

    @Override
    public void aggiungiIngrediente(String nome, int quantita) {
        LOGGER.log(Level.INFO, "InventarioDao: Aggiungi ingrediente {0} con quantità {1}", new Object[]{nome, quantita});
        inventario.put(nome, inventario.getOrDefault(nome, 0) + quantita);
        if (!useDemo) {
            LOGGER.log(Level.INFO, "InventarioDao: Persistenza abilitata, salvataggio...");
            salvaInventario();
        }
    }

    @Override
    public void rimuoviIngrediente(String nome, int quantita) {
        LOGGER.log(Level.INFO, "InventarioDao: Rimuovi ingrediente {0} con quantità {1}", new Object[]{nome, quantita});
        if (inventario.containsKey(nome)) {
            int currentQuantity = inventario.get(nome);
            if (currentQuantity > quantita) {
                inventario.put(nome, currentQuantity - quantita);
            } else {
                inventario.remove(nome);
            }
            if (!useDemo) {
                LOGGER.log(Level.INFO, "InventarioDao: Persistenza abilitata, salvataggio...");
                salvaInventario();
            }
        }
    }

    @Override
    public Map<String, Integer> getInventario() {
        LOGGER.log(Level.INFO, "InventarioDao: Recupero inventario");
        return new HashMap<>(inventario);
    }

    private void salvaInventario() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            LOGGER.log(Level.INFO, "InventarioDao: Salvataggio inventario su file...");
            for (Map.Entry<String, Integer> entry : inventario.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "InventarioDao: Errore durante il salvataggio dell'inventario", e);
        }
    }

    private void loadInventario() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            LOGGER.log(Level.INFO, "InventarioDao: Caricamento inventario dal file...");
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String nome = parts[0];
                    int quantita = Integer.parseInt(parts[1]);
                    inventario.put(nome, quantita);
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "InventarioDao: Errore durante il caricamento dell'inventario", e);
        }
    }
}
