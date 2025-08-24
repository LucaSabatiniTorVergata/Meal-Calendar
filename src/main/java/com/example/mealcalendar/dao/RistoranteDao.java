package com.example.mealcalendar.dao;

import com.example.mealcalendar.model.RistoranteEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RistoranteDao {

    private static final String FILE_NAME = "ristoranti.txt";

    public void aggiungiRistorante(RistoranteEntity ristorante) throws IOException {
        String record = ristorante.getNome() + "," +
                ristorante.getIndirizzo() + "," +
                ristorante.getPostiDisponibili() + "," +
                ristorante.getTipologiaRistorante().name();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(record);
            writer.newLine();
        }
    }

    public List<RistoranteEntity> leggiRistoranti() {
        List<RistoranteEntity> ristoranti = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) return ristoranti;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] campi = line.split(",");
                if (campi.length == 4) {
                    RistoranteEntity entity = new RistoranteEntity();
                    entity.setNome(campi[0]);
                    entity.setIndirizzo(campi[1]);
                    entity.setPostiDisponibili(Integer.parseInt(campi[2]));
                    entity.setTipologiaRistorante(Enum.valueOf(
                            com.example.mealcalendar.model.TipologiaRistorante.class, campi[3]));
                    ristoranti.add(entity);
                }
            }
        } catch (IOException e) {
            System.err.println("Errore lettura file ristoranti: " + e.getMessage());
        }

        return ristoranti;
    }
}
