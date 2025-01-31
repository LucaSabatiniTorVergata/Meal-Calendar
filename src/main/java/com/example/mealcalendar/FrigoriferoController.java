package com.example.mealcalendar;

import java.util.Map;

public class FrigoriferoController {
    private static Frigorifero frigorifero = new Frigorifero();

    public static Frigorifero getFrigorifero() {
        return frigorifero;
    }

    public void aggiungiIngrediente(FrigoBean frigoBean) {
        String nomeIngrediente = frigoBean.getNomeIngrediente();
        int quantita = frigoBean.getQuantita();

        frigorifero.aggiungiIngrediente(nomeIngrediente, quantita);
    }

    public Map<String, Integer> getInventario() {
        return frigorifero.getInventario();
    }
}
