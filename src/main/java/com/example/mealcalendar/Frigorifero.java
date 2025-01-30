package com.example.mealcalendar;

import java.util.ArrayList;
import java.util.List;

public class Frigorifero {
    private List<Ingrediente> inventario;

    public Frigorifero() {
        this.inventario = new ArrayList<>();
    }

    public void aggiungiIngrediente(Ingrediente ingrediente) {
        inventario.add(ingrediente);
    }

    public List<Ingrediente> getInventario() {
        return inventario;
    }
    public void stampaInventario() {
        if (inventario.isEmpty()) {
            System.out.println("Il frigorifero è vuoto.");
        } else {
            System.out.println("Inventario del frigorifero:");
            for (Ingrediente ingrediente : inventario) {
                System.out.println(ingrediente.getNome());
            }
        }
    }
}
