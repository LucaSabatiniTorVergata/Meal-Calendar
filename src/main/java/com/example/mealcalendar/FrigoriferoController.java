package com.example.mealcalendar;

import java.io.IOException;
import java.util.Map;

public class FrigoriferoController {
    private InventarioDao inventarioDao;

    // Costruttore che inizializza la modalità di persistenza
    public FrigoriferoController(boolean usePersistence) throws IOException {
        this.inventarioDao = InventarioDao.getInstance(usePersistence);
    }

    // Metodo per cambiare la modalità di persistenza
    public void setUsePersistence(boolean usePersistence) throws IOException {
        this.inventarioDao = InventarioDao.getInstance(usePersistence);
    }

    // Aggiunge un ingrediente
    public void aggiungiIngrediente(String nome, int quantita) throws IOException {
        inventarioDao.aggiungiIngrediente(nome, quantita);
        if (inventarioDao.isPersistenceEnabled()) {
            inventarioDao.salvaInventario();  // Salva solo se la persistenza è abilitata
        }
    }

    // Rimuove un ingrediente
    public void rimuoviIngrediente(String nome, int quantita) throws IOException {
        inventarioDao.rimuoviIngrediente(nome, quantita);
        if (inventarioDao.isPersistenceEnabled()) {
            inventarioDao.salvaInventario();  // Salva solo se la persistenza è abilitata
        }
    }

    // Restituisce l'inventario
    public Map<String, Integer> getInventario() {
        return inventarioDao.getInventario();
    }
}
