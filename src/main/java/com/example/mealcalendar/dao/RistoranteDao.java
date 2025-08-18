package com.example.mealcalendar.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RistoranteDao {

    public List<String> leggiRistoranti() {
        List<String> ristoranti = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/restaurants.txt")))) {

            String line;
            while ((line = br.readLine()) != null) {
                ristoranti.add(line);  // formato già "nome:indirizzo:tipouser"
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

        return ristoranti;
    }
}
