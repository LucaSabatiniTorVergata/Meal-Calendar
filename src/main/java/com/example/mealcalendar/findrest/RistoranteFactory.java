package com.example.mealcalendar.findrest;

public class RistoranteFactory {

    public static Ristorante creaRistorante(String id, String nome, String password,
                                            int tavoliDisponibili, boolean isAperto, String citta) {
        return new Ristorante(id, nome, password, tavoliDisponibili, 0, isAperto, citta);
    }
}
