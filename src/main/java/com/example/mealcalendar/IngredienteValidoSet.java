package com.example.mealcalendar;

import java.util.HashSet;
import java.util.Set;

public class IngredienteValidoSet {

    private static IngredienteValidoSet instance = null;
    private Set<String> ingredientiValidi;

    private IngredienteValidoSet() {
        ingredientiValidi = new HashSet<>();
        ingredientiValidi.add("latte");
        ingredientiValidi.add("burro");
        ingredientiValidi.add("uova");
        ingredientiValidi.add("pomodoro");
        ingredientiValidi.add("farina");
        // Aggiungi altri ingredienti validi qui
    }

    public static IngredienteValidoSet getInstance() {
        if (instance == null) {
            instance = new IngredienteValidoSet();
        }
        return instance;
    }

    public boolean isIngredienteValido(String nomeIngrediente) {
        return ingredientiValidi.contains(nomeIngrediente);
    }
}
