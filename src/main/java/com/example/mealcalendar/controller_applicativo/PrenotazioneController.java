package com.example.mealcalendar.controller_applicativo;

import com.example.mealcalendar.bean.PrenotazioneBean;
import com.example.mealcalendar.bean.RistoranteBean;
import com.example.mealcalendar.dao.PrenotazioneDao;
import com.example.mealcalendar.dao.RistoranteDao;

import java.util.List;

public class PrenotazioneController {

    private final RistoranteDao ristoranteDao = new RistoranteDao();
    private final PrenotazioneDao prenotazioneDao = new PrenotazioneDao();

    // Restituisce tutti i ristoranti
    public List<RistoranteBean> getRistoranti() {
        return ristoranteDao.leggiRistoranti();
    }

    // Salva una prenotazione senza aggiornare i posti
    public void salvaPrenotazione(PrenotazioneBean prenotazione) {
        prenotazioneDao.salvaPrenotazione(prenotazione);
    }

    // Restituisce tutte le prenotazioni
    public List<PrenotazioneBean> getPrenotazioni() {
        return prenotazioneDao.leggiPrenotazioni();
    }

    // Nel PrenotazioneController
    public boolean eliminaPrenotazione(PrenotazioneBean prenotazione) {
        if (prenotazione == null) return false;
        return prenotazioneDao.eliminaPrenotazione(String.valueOf(prenotazione.getId()));
    }


}
