package com.example.mealcalendar;

import java.io.IOException;
import java.util.Map;

public interface InventarioDaoInterface {
    void aggiungiIngrediente(String nome, int quantita) throws IOException;
    void rimuoviIngrediente(String nome, int quantita) throws IOException;
    Map<String, Integer> getInventario() throws IOException;
}
