package com.example.mealcalendar;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class InventarioDao {

    private static InventarioDao instance;
    private boolean usePersistence;
    private Map<String, Integer> inventario;
    private static final String FILE_PATH = "inventario.txt";

    private InventarioDao(boolean usePersistence) {
        this.usePersistence = usePersistence;
        this.inventario = new HashMap<>();
        if (usePersistence) {
            loadInventario();
        }
    }

    // Singleton pattern per gestire l'istanza unica di InventarioDao
    public static InventarioDao getInstance(boolean usePersistence) {
        if (instance == null) {
            instance = new InventarioDao(usePersistence);
        }
        return instance;
    }

    // Aggiunge ingrediente all'inventario
    public void aggiungiIngrediente(String nome, int quantita) {
        inventario.put(nome, inventario.getOrDefault(nome, 0) + quantita);
    }

    // Rimuove ingrediente dall'inventario
    public void rimuoviIngrediente(String nome, int quantita) {
        if (inventario.containsKey(nome)) {
            int currentQuantity = inventario.get(nome);
            if (currentQuantity > quantita) {
                inventario.put(nome, currentQuantity - quantita);
            } else {
                inventario.remove(nome);
            }
        }
    }

    // Restituisce l'inventario
    public Map<String, Integer> getInventario() {
        return new HashMap<>(inventario);
    }

    // Verifica se la persistenza è abilitata
    public boolean isPersistenceEnabled() {
        return usePersistence;
    }

    // Salva l'inventario nel file
    public void salvaInventario() {
        if (usePersistence) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (Map.Entry<String, Integer> entry : inventario.entrySet()) {
                    writer.write(entry.getKey() + ":" + entry.getValue());
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Carica l'inventario dal file
    private void loadInventario() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
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
            e.printStackTrace();
        }
    }
}
