package com.example.mealcalendar;

import java.util.Map;

public class FrigoriferoController {
    private static Frigorifero frigorifero = new Frigorifero();

    public static Frigorifero getFrigorifero() {
        return frigorifero;
    }

    public void aggiungiIngrediente(String nomeIngrediente, int quantita) {
        frigorifero.aggiungiIngrediente(nomeIngrediente, quantita); // ✅ Passiamo nome e quantità, non un oggetto
    }

    public Map<String, Integer> getInventario() {
        return frigorifero.getInventario(); // ✅ Ritorniamo la mappa corretta
    }

    public void stampaInventario() {
        frigorifero.stampaInventario(); // ✅ Chiamiamo il metodo corretto di Frigorifero
    }
}
