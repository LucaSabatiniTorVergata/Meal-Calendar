package com.example.mealcalendar;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrigoriferoController {
    private static final Logger LOGGER = Logger.getLogger(FrigoriferoController.class.getName());
    private InventarioDao inventarioDao;

    public FrigoriferoController(boolean usePersistence) {
        LOGGER.log(Level.INFO, "FrigoriferoController: Inizializzazione con persistenza {0}", usePersistence);
        this.inventarioDao = InventarioDao.getInstance(usePersistence);
    }

    public void aggiungiIngrediente(String nome, int quantita) {
        LOGGER.log(Level.INFO, "FrigoriferoController: Aggiungi ingrediente {0} con quantità {1}", new Object[]{nome, quantita});
        inventarioDao.aggiungiIngrediente(nome, quantita);
    }

    public void rimuoviIngrediente(String nome, int quantita)  {
        LOGGER.log(Level.INFO, "FrigoriferoController: Rimuovi ingrediente {0} con quantità {1}", new Object[]{nome, quantita});
        inventarioDao.rimuoviIngrediente(nome, quantita);
    }

    public Map<String, Integer> getInventario() {
        LOGGER.log(Level.INFO, "FrigoriferoController: Recupero inventario");
        return inventarioDao.getInventario();
    }

}
