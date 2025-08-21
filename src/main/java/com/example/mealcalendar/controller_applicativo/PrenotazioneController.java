package com.example.mealcalendar.controller_applicativo;

import com.example.mealcalendar.bean.RistoranteBean;
import com.example.mealcalendar.dao.RistoranteDao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PrenotazioneController {

    private final RistoranteDao ristoranteDao = new RistoranteDao();

    public List<RistoranteBean> getRistoranti() {
        return ristoranteDao.leggiRistoranti();
    }

    // Metodo per salvare una prenotazione nel file prenotazioni.txt
    public void salvaPrenotazione(String usernameUtente, String nomeRistorante, String data, String ora) {
        String prenotazione = usernameUtente + "," + nomeRistorante + "," + data + "," + ora;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("prenotazioni.txt", true))) {
            writer.write(prenotazione);
            writer.newLine(); // va a capo dopo ogni prenotazione
            System.out.println("Prenotazione salvata correttamente.");
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio della prenotazione: " + e.getMessage());
        }
    }
}
