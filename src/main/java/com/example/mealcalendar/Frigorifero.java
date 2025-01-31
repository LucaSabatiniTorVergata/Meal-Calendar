package com.example.mealcalendar;

import java.util.ArrayList;
import java.util.List;

public class Frigorifero {
    private List<Ingrediente> inventario;

    public Frigorifero() {
        this.inventario = new ArrayList<>();
    }

    public void aggiungiIngrediente(Ingrediente ingrediente) {
        boolean trovato = false;

        // Controlla se l'ingrediente esiste già
        for (Ingrediente i : inventario) {
            if (i.getNome().equals(ingrediente.getNome())) {
                i.incrementaQuantita(ingrediente.getQuantita());  // Incrementa la quantità se già esiste
                trovato = true;
                break;
            }
        }

        // Se non trovato, aggiungiamo l'ingrediente
        if (!trovato) {
            inventario.add(ingrediente);  // Aggiungi come nuovo ingrediente
        }
    }

    public List<Ingrediente> getInventario() {
        return inventario;
    }

    // Metodo per stampare l'inventario con le quantità
    public void stampaInventario() {
        if (inventario.isEmpty()) {
            System.out.println("Il frigorifero è vuoto.");
        } else {
            System.out.println("Inventario del frigorifero:");
            for (Ingrediente ingrediente : inventario) {
                System.out.println(ingrediente.toString());  // Stampa anche la quantità
            }
        }
    }
}

