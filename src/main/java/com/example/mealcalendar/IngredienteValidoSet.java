package com.example.mealcalendar;

import java.util.HashSet;
import java.util.Set;

public class IngredienteValidoSet {
    // Insieme di ingredienti validi (tutti in minuscolo)
    private static IngredienteValidoSet instance = null;
    private Set<String> ingredientiValidi;

    // Costruttore privato per evitare che vengano create altre istanze
    private IngredienteValidoSet() {
        ingredientiValidi = new HashSet<>();
        // Aggiungi gli ingredienti validi (in minuscolo)
        ingredientiValidi.add("pomodoro");
        ingredientiValidi.add("mozzarella");
        ingredientiValidi.add("basilico");
        ingredientiValidi.add("olio");
        ingredientiValidi.add("sale");
        ingredientiValidi.add("pepe");
        ingredientiValidi.add("farina");
        // Aggiungi qui gli ingredienti che desideri
    }

    // Metodo per ottenere l'istanza Singleton
    public static IngredienteValidoSet getInstance() {
        if (instance == null) {
            instance = new IngredienteValidoSet(); // Crea l'istanza solo se non esiste già
        }
        return instance;
    }

    // Metodo per verificare se un ingrediente è valido
    public boolean isIngredienteValido(String ingrediente) {
        return ingredientiValidi.contains(ingrediente.toLowerCase());
    }
}
