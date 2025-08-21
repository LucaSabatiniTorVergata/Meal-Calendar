package com.example.mealcalendar.dao;

import com.example.mealcalendar.bean.RistoranteBean;
import com.example.mealcalendar.model.TipologiaRistorante;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RistoranteDao {

    private static final String FILE_NAME = "ristoranti.txt";

    public List<RistoranteBean> leggiRistoranti() {
        List<RistoranteBean> ristoranti = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) return ristoranti;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split("-");

                if (fields.length < 3) continue;

                String nome = fields[0];
                String indirizzo = fields[1];

                int postiDisponibili;
                try {
                    postiDisponibili = Integer.parseInt(fields[2].trim());
                } catch (NumberFormatException e) {
                    postiDisponibili = 0;
                }

                TipologiaRistorante tipologia;
                try {
                    tipologia = TipologiaRistorante.valueOf(fields[fields.length - 1].trim().toUpperCase());
                } catch (IllegalArgumentException e) {
                    tipologia = TipologiaRistorante.ONNIVORO;
                }

                ristoranti.add(new RistoranteBean(nome, indirizzo, postiDisponibili, tipologia));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ristoranti;
    }

    public void aggiungiRistorante(RistoranteBean ristorante) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            String line = String.join("-",
                    ristorante.getNome(),
                    ristorante.getIndirizzo(),
                    String.valueOf(ristorante.getPostiDisponibili()),
                    ristorante.getTipologiaRistorante().name()
            );
            bw.write(line);
            bw.newLine();
        }
    }
}
