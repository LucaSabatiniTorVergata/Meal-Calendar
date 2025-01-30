package com.example.mealcalendar;
import java.util.*;



public class IngredienteFactory {
    private static Map<String, Ingrediente> ingredientiCreati = new HashMap<>();

    public static Ingrediente creaIngrediente(String nome) {
        return ingredientiCreati.computeIfAbsent(nome, Ingrediente::new);
    }

    public static Collection<Ingrediente> getIngredientiCreati() {
        return ingredientiCreati.values();
    }
}
