package com.example.mealcalendar.controller_applicativo;

import com.example.mealcalendar.bean.PrenotazioneBean;
import com.example.mealcalendar.bean.RistoranteBean;
import com.example.mealcalendar.dao.PrenotazioneDao;
import com.example.mealcalendar.dao.RistoranteDao;

import java.time.LocalDate;
import java.util.List;

public class PrenotazioneController {

    private final RistoranteDao ristoranteDao = new RistoranteDao();
    private final PrenotazioneDao prenotazioneDao = new PrenotazioneDao();

    public List<RistoranteBean> getRistoranti() {
        return ristoranteDao.leggiRistoranti();
    }

    public void salvaPrenotazione(PrenotazioneBean prenotazione) {
        prenotazioneDao.salvaPrenotazione(prenotazione);
    }

    public List<PrenotazioneBean> getPrenotazioni() {
        List<PrenotazioneBean> prenotazioni = prenotazioneDao.leggiPrenotazioni();
        LocalDate oggi = LocalDate.now();

        for (PrenotazioneBean p : prenotazioni) {
            p.setScaduta(p.getDataPrenotazione().isBefore(oggi));
        }

        return prenotazioni;
    }

    public boolean eliminaPrenotazione(PrenotazioneBean prenotazione) {
        if (prenotazione == null) return false;
        return prenotazioneDao.eliminaPrenotazione(String.valueOf(prenotazione.getId()));
    }
}
