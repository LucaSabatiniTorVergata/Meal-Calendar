package com.example.mealcalendar;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class InventarioDao {

    private static InventarioDao instance;
    private boolean usePersistence;
    private Map<String, Integer> inventario;
    private static final String FILE_PATH = "inventario.txt";  // Assicurati che il percorso sia corretto

    private InventarioDao(boolean usePersistence) {
        System.out.println("InventarioDao: Inizializzazione con persistenza " + usePersistence);
        this.usePersistence = usePersistence;
        this.inventario = new HashMap<>();
        if (usePersistence) {
            loadInventario();  // Carica l'inventario dal file se la persistenza è attiva
        }
    }

    public static InventarioDao getInstance(boolean usePersistence) {
        if (instance == null) {
            System.out.println("InventarioDao: Creazione della nuova istanza con persistenza " + usePersistence);
            instance = new InventarioDao(usePersistence);
        }
        return instance;
    }

    public void aggiungiIngrediente(String nome, int quantita) {
        System.out.println("InventarioDao: Aggiungi ingrediente " + nome + " con quantità " + quantita);
        inventario.put(nome, inventario.getOrDefault(nome, 0) + quantita);
        if (usePersistence) {
            System.out.println("InventarioDao: Persistenza abilitata, salvataggio...");
            salvaInventario();
        }
    }

    public void rimuoviIngrediente(String nome, int quantita) {
        System.out.println("InventarioDao: Rimuovi ingrediente " + nome + " con quantità " + quantita);
        if (inventario.containsKey(nome)) {
            int currentQuantity = inventario.get(nome);
            if (currentQuantity > quantita) {
                inventario.put(nome, currentQuantity - quantita);
            } else {
                inventario.remove(nome);
            }
            if (usePersistence) {
                System.out.println("InventarioDao: Persistenza abilitata, salvataggio...");
                salvaInventario();
            }
        }
    }

    public Map<String, Integer> getInventario() {
        System.out.println("InventarioDao: Recupero inventario");
        return new HashMap<>(inventario);  // Restituisci una copia per evitare modifiche dirette
    }

    private void salvaInventario() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            System.out.println("InventarioDao: Salvataggio inventario su file...");
            for (Map.Entry<String, Integer> entry : inventario.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadInventario() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            System.out.println("InventarioDao: Caricamento inventario dal file...");
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

    public boolean isPersistenceEnabled() {
        System.out.println("InventarioDao: Verifica se la persistenza è abilitata: " + usePersistence);
        return usePersistence;
    }
}
