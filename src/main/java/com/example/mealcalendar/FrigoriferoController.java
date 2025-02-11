package com.example.mealcalendar;

import java.io.IOException;
import java.util.Map;

public class FrigoriferoController {
    private InventarioDao inventarioDao;

    public FrigoriferoController(boolean usePersistence) throws IOException {
        System.out.println("FrigoriferoController: Inizializzazione con persistenza " + usePersistence);
        this.inventarioDao = InventarioDao.getInstance(usePersistence);
    }


    public void aggiungiIngrediente(String nome, int quantita) throws IOException {
        System.out.println("FrigoriferoController: Aggiungi ingrediente " + nome + " con quantità " + quantita);
        inventarioDao.aggiungiIngrediente(nome, quantita);
    }

    public void rimuoviIngrediente(String nome, int quantita) throws IOException {
        System.out.println("FrigoriferoController: Rimuovi ingrediente " + nome + " con quantità " + quantita);
        inventarioDao.rimuoviIngrediente(nome, quantita);
    }

    public Map<String, Integer> getInventario() {
        System.out.println("FrigoriferoController: Recupero inventario");
        return inventarioDao.getInventario();
    }

    public boolean isPersistenceEnabled() {
        System.out.println("FrigoriferoController: Verifica se la persistenza è abilitata");
        return inventarioDao.isPersistenceEnabled();
    }
}
