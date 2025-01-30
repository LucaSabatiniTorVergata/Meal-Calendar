package com.example.mealcalendar;
import java.util.*;

public class Frigorifero {
    private Map<String, Integer> inventario;

    public Frigorifero() {
        this.inventario = new HashMap<>();
    }

    public void aggiungiIngrediente(String nome) {
        Ingrediente ingrediente = IngredienteFactory.creaIngrediente(nome);
        inventario.put(ingrediente.getNome(), inventario.getOrDefault(ingrediente.getNome(), 0) + 1);
    }

    public Map<String, Integer> getInventario() {
        return inventario;
    }
}
