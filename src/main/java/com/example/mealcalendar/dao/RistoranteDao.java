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
                if (fields.length >= 5) {
                    String username = fields[0];
                    String email = fields[1];
                    String password = fields[2];
                    String ruolo = fields[3];
                    TipologiaRistorante tipologia = TipologiaRistorante.valueOf(fields[4]);
                    ristoranti.add(new RistoranteBean(username, email, password, ruolo, tipologia));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ristoranti;
    }

    public void aggiungiRistorante(RistoranteBean ristorante) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            String line = String.join("-",
                    ristorante.getUsername(),
                    ristorante.getEmail(),
                    ristorante.getPassword(),
                    ristorante.getRuolo(),
                    ristorante.getTipologiaRistorante().name());
            bw.write(line);
            bw.newLine();
        }
    }
}
