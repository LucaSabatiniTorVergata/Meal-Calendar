package com.example.mealcalendar;
import java.util.*;

public class IngredienteFactory {
    // Mappa per tenere traccia degli ingredienti già creati
    private static Map<String, Ingrediente> ingredientiCreati = new HashMap<>();

    // Modificato il metodo per prendere la quantità come parametro
    public static Ingrediente creaIngrediente(String nome, int quantita) {
        // Usa una lambda per creare l'ingrediente se non esiste già
        return ingredientiCreati.computeIfAbsent(nome, k -> new Ingrediente(k, quantita));
    }

    // Metodo per ottenere tutti gli ingredienti creati
    public static Collection<Ingrediente> getIngredientiCreati() {
        return ingredientiCreati.values();
    }
}

