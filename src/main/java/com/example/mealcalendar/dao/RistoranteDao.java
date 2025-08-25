package com.example.mealcalendar.dao;

import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.bean.RistoranteBean;
import com.example.mealcalendar.model.RistoranteEntity;
import com.example.mealcalendar.exception.PrenotazioneException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RistoranteDao {

    private static final String FILE_NAME = "ristoranti.txt";
    private static final List<RistoranteEntity> ramRistoranti = new ArrayList<>();

    public void aggiungiRistorante(RistoranteEntity ristorante) {
        if (SessionManagerSLT.getInstance().getPersistenceType() != null &&
                SessionManagerSLT.getInstance().getPersistenceType().name().equals("RAM")) {
            ramRistoranti.add(ristorante);
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
                writer.write(ristorante.getNome() + "," + ristorante.getIndirizzo() + "," +
                        ristorante.getPostiDisponibili() + "," + ristorante.getTipologiaRistorante().name());
                writer.newLine();
            } catch (IOException e) {
                System.err.println("Errore scrittura file ristoranti: " + e.getMessage());
            }
        }
    }

    public List<RistoranteEntity> leggiRistoranti() {
        if (SessionManagerSLT.getInstance().getPersistenceType() != null &&
                SessionManagerSLT.getInstance().getPersistenceType().name().equals("RAM")) {
            return new ArrayList<>(ramRistoranti);
        }

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

    public RistoranteBean getRistoranteByName(String nome) {
        return leggiRistoranti().stream()
                .filter(r -> r.getNome().equals(nome))
                .map(r -> new RistoranteBean(r.getNome(), r.getIndirizzo(), r.getPostiDisponibili(), r.getTipologiaRistorante()))
                .findFirst()
                .orElse(null);
    }

    public void aggiornaPosti(RistoranteBean ristorante) throws PrenotazioneException {
        if (SessionManagerSLT.getInstance().getPersistenceType() != null &&
                SessionManagerSLT.getInstance().getPersistenceType().name().equals("RAM")) {
            List<RistoranteEntity> lista = leggiRistoranti();
            for (RistoranteEntity r : lista) {
                if (r.getNome().equals(ristorante.getNome())) {
                    r.setPostiDisponibili(ristorante.getPostiDisponibili());
                    return;
                }
            }
        } else {
            // Scrive tutti i ristoranti aggiornati sul file
            List<RistoranteEntity> lista = leggiRistoranti();
            boolean trovato = false;
            for (RistoranteEntity r : lista) {
                if (r.getNome().equals(ristorante.getNome())) {
                    r.setPostiDisponibili(ristorante.getPostiDisponibili());
                    trovato = true;
                    break;
                }
            }
            if (!trovato) {
                throw new PrenotazioneException("Ristorante non trovato per aggiornamento posti.");
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
                for (RistoranteEntity r : lista) {
                    writer.write(r.getNome() + "," + r.getIndirizzo() + "," +
                            r.getPostiDisponibili() + "," + r.getTipologiaRistorante().name());
                    writer.newLine();
                }
            } catch (IOException e) {
                throw new PrenotazioneException("Errore aggiornamento posti ristorante.", e);
            }
        }
    }
}
