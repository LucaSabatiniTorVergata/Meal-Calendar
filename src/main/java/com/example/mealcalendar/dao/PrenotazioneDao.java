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
    private static final String ERR_LETTURA_FILE = "Errore lettura file prenotazioni: ";

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
        File filePrenotazioni = new File(FILE_NAME);

        if (!filePrenotazioni.exists()) return prenotazioni;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePrenotazioni))) {
            String riga;
            while ((riga = reader.readLine()) != null) {
                String[] campi = riga.split(",");
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
            System.err.println(ERR_LETTURA_FILE + e.getMessage());
        }

        return prenotazioni;
    }

    private int getUltimoId() {
        File filePrenotazioni = new File(FILE_NAME);
        if (!filePrenotazioni.exists()) return 0;

        String ultimaRiga = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePrenotazioni))) {
            String riga;
            while ((riga = reader.readLine()) != null) ultimaRiga = riga;
        } catch (IOException e) {
            System.err.println(ERR_LETTURA_FILE + e.getMessage());
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

        File filePrenotazioni = new File(FILE_NAME);
        if (!filePrenotazioni.exists()) return false;

        List<String> righeRimanenti = new ArrayList<>();
        boolean trovata = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePrenotazioni))) {
            String riga;
            while ((riga = reader.readLine()) != null) {
                if (!riga.startsWith(idPrenotazione + ",")) {
                    righeRimanenti.add(riga);
                } else {
                    trovata = true;
                }
            }
        } catch (IOException e) {
            System.err.println(ERR_LETTURA_FILE + e.getMessage());
            return false;
        }

        if (trovata) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePrenotazioni))) {
                for (String riga : righeRimanenti) {
                    writer.write(riga);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println("Errore scrittura file prenotazioni: " + e.getMessage());
                return false;
            }
        }

        return trovata;
    }
}
