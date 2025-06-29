package com.example.mealcalendar.findrest;

public class RecensioneFactory {

    public static Recensione creaRecensione(String id, String ristoranteId,
                                            String username, String testo, int voto) {
        if (voto < 1 || voto > 5) {
            throw new IllegalArgumentException("Il voto deve essere compreso tra 1 e 5.");
        }
        return new Recensione(id, ristoranteId, username, testo, voto);
    }
}
