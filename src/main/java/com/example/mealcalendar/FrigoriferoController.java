package com.example.mealcalendar;

import java.io.IOException;
import java.util.Map;

public class FrigoriferoController {
    private InventarioDao inventarioDao;

    public FrigoriferoController(boolean usePersistence) throws IOException {
        this.inventarioDao = InventarioDao.getInstance(usePersistence);
    }

    public void aggiungiIngrediente(String nome, int quantita) throws IOException {
        inventarioDao.aggiungiIngrediente(nome, quantita);
    }

    public void rimuoviIngrediente(String nome, int quantita) throws IOException {
        inventarioDao.rimuoviIngrediente(nome, quantita);
    }

    public Map<String, Integer> getInventario() {
        return inventarioDao.getInventario();
    }

    public void stampaInventario() {
        inventarioDao.stampaInventario();
    }
}
