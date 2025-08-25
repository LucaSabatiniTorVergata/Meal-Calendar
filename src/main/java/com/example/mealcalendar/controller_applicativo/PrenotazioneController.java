package com.example.mealcalendar.controller_applicativo;

import com.example.mealcalendar.bean.PrenotazioneBean;
import com.example.mealcalendar.bean.RistoranteBean;
import com.example.mealcalendar.dao.PrenotazioneDao;
import com.example.mealcalendar.dao.RistoranteDao;
import com.example.mealcalendar.exception.PrenotazioneException;
import com.example.mealcalendar.factory.PrenotazioneFactory;
import com.example.mealcalendar.factory.RistoranteFactory;
import com.example.mealcalendar.model.PrenotazioneEntity;
import com.example.mealcalendar.model.RistoranteEntity;

import java.time.LocalDate;
import java.util.List;

public class PrenotazioneController {

    private final RistoranteDao ristoranteDao = new RistoranteDao();
    private final PrenotazioneDao prenotazioneDao = new PrenotazioneDao();

    public List<RistoranteBean> getRistoranti() throws PrenotazioneException {
        try {
            List<RistoranteEntity> entities = ristoranteDao.leggiRistoranti();
            if (entities.isEmpty()) {
                throw new PrenotazioneException("Nessun ristorante disponibile.");
            }
            return RistoranteFactory.entitiesToBeans(entities);
        } catch (Exception e) {
            throw new PrenotazioneException("Errore durante il recupero dei ristoranti", e);
        }
    }

    public void salvaPrenotazione(PrenotazioneBean prenotazioneBean) throws PrenotazioneException {
        if (prenotazioneBean.getPostiASedere() <= 0) {
            throw new PrenotazioneException("Il numero di posti a sedere deve essere maggiore di zero.");
        }

        // Recupera ristorante dal DAO
        RistoranteBean ristorante = ristoranteDao.getRistoranteByName(prenotazioneBean.getNomeRistorante());
        if (ristorante == null) {
            throw new PrenotazioneException("Ristorante non trovato.");
        }

        // Logica dello scalamento dei posti nel controller
        int postiDisponibili = ristorante.getPostiDisponibili();
        if (postiDisponibili < prenotazioneBean.getPostiASedere()) {
            throw new PrenotazioneException("Posti insufficienti per la prenotazione.");
        }
        int nuoviPosti = postiDisponibili - prenotazioneBean.getPostiASedere();
        ristorante.setPostiDisponibili(nuoviPosti);

        // Aggiorna il DAO
        ristoranteDao.aggiornaPosti(ristorante);

        // Salva la prenotazione
        PrenotazioneEntity entity = PrenotazioneFactory.beanToEntity(prenotazioneBean);
        prenotazioneDao.salvaPrenotazione(entity);
    }

    public List<PrenotazioneBean> getPrenotazioni() throws PrenotazioneException {
        try {
            List<PrenotazioneEntity> entities = prenotazioneDao.leggiPrenotazioni();
            LocalDate oggi = LocalDate.now();

            for (PrenotazioneEntity e : entities) {
                e.setScaduta(e.getDataPrenotazione().isBefore(oggi));
            }

            return entities.stream()
                    .map(PrenotazioneFactory::entityToBean)
                    .toList();
        } catch (Exception e) {
            throw new PrenotazioneException("Errore durante il recupero delle prenotazioni.", e);
        }
    }

    public boolean eliminaPrenotazione(PrenotazioneBean prenotazioneBean) throws PrenotazioneException {
        if (prenotazioneBean == null) {
            throw new PrenotazioneException("Prenotazione nulla non può essere eliminata.");
        }
        try {
            return prenotazioneDao.eliminaPrenotazione(String.valueOf(prenotazioneBean.getId()));
        } catch (Exception e) {
            throw new PrenotazioneException("Errore durante l'eliminazione della prenotazione.", e);
        }
    }
}
