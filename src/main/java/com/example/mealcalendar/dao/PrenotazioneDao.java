package com.example.mealcalendar.dao;

import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.model.PrenotazioneEntity;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrenotazioneDao {

    private static final String FILE_NAME = "prenotazioni.txt";
    private static final List<PrenotazioneEntity> ramPrenotazioni = new ArrayList<>();

    public void salvaPrenotazione(PrenotazioneEntity prenotazione) {
        if (SessionManagerSLT.getInstance().getPersistenceType() == com.example.mealcalendar.PersistenceType.RAM) {
            prenotazione.setId(ramPrenotazioni.size() + 1);
            ramPrenotazioni.add(prenotazione);
            return;
        }

        int nuovoId = getUltimoId() + 1;
        prenotazione.setId(nuovoId);

        String record = prenotazione.getId() + "," +
                prenotazione.getUsernameUtente() + "," +
                prenotazione.getNomeRistorante() + "," +
                prenotazione.getDataPrenotazione() + "," +
                prenotazione.getDataScadenza() + "," +
                prenotazione.getOraPrenotazione() + "," +
                prenotazione.getPostiASedere();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(record);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio della prenotazione: " + e.getMessage());
        }
    }

    public List<PrenotazioneEntity> leggiPrenotazioni() {
        if (SessionManagerSLT.getInstance().getPersistenceType() == com.example.mealcalendar.PersistenceType.RAM) {
            return new ArrayList<>(ramPrenotazioni);
        }

        List<PrenotazioneEntity> prenotazioni = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) return prenotazioni;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campi = linea.split(",");
                if (campi.length == 7) {
                    PrenotazioneEntity prenotazione = new PrenotazioneEntity(
                            Integer.parseInt(campi[0]),
                            LocalDate.parse(campi[3]),
                            LocalDate.parse(campi[4]),
                            campi[5],
                            campi[1],
                            campi[2],
                            Integer.parseInt(campi[6])
                    );
                    prenotazioni.add(prenotazione);
                }
            }
        } catch (IOException e) {
            System.err.println("Errore lettura file prenotazioni: " + e.getMessage());
        }

        return prenotazioni;
    }

    private int getUltimoId() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return 0;

        String ultimaRiga = null;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) ultimaRiga = linea;
        } catch (IOException e) {
            System.err.println("Errore lettura file prenotazioni: " + e.getMessage());
        }

        if (ultimaRiga != null) {
            String[] campi = ultimaRiga.split(",");
            try {
                return Integer.parseInt(campi[0]);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    public boolean eliminaPrenotazione(String idPrenotazione) {
        if (SessionManagerSLT.getInstance().getPersistenceType() == com.example.mealcalendar.PersistenceType.RAM) {
            return ramPrenotazioni.removeIf(p -> String.valueOf(p.getId()).equals(idPrenotazione));
        }

        File file = new File(FILE_NAME);
        if (!file.exists()) return false;

        List<String> righe = new ArrayList<>();
        boolean trovata = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.startsWith(idPrenotazione + ",")) {
                    righe.add(linea);
                } else {
                    trovata = true;
                }
            }
        } catch (IOException e) {
            System.err.println("Errore lettura file prenotazioni: " + e.getMessage());
            return false;
        }

        if (trovata) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (String r : righe) {
                    bw.write(r);
                    bw.newLine();
                }
            } catch (IOException e) {
                System.err.println("Errore scrittura file prenotazioni: " + e.getMessage());
                return false;
            }
        }

        return trovata;
    }
}
