package com.example.mealcalendar.controller_applicativo;

import com.example.mealcalendar.bean.PrenotazioneBean;
import com.example.mealcalendar.bean.RistoranteBean;
import com.example.mealcalendar.dao.PrenotazioneDao;
import com.example.mealcalendar.dao.RistoranteDao;
import com.example.mealcalendar.factory.PrenotazioneFactory;
import com.example.mealcalendar.factory.RistoranteFactory;
import com.example.mealcalendar.model.PrenotazioneEntity;
import com.example.mealcalendar.model.RistoranteEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PrenotazioneController {

    private final RistoranteDao ristoranteDao = new RistoranteDao();
    private final PrenotazioneDao prenotazioneDao = new PrenotazioneDao();

    public List<RistoranteBean> getRistoranti() {
        List<RistoranteEntity> entities = ristoranteDao.leggiRistoranti();
        return RistoranteFactory.entitiesToBeans(entities);
    }

    public void salvaPrenotazione(PrenotazioneBean prenotazioneBean) {
        PrenotazioneEntity entity = PrenotazioneFactory.beanToEntity(prenotazioneBean);
        prenotazioneDao.salvaPrenotazione(entity);
    }

    public List<PrenotazioneBean> getPrenotazioni() {
        List<PrenotazioneEntity> entities = prenotazioneDao.leggiPrenotazioni();
        LocalDate oggi = LocalDate.now();

        for (PrenotazioneEntity e : entities) {
            e.setScaduta(e.getDataPrenotazione().isBefore(oggi));
        }

        return entities.stream()
                .map(PrenotazioneFactory::entityToBean)
                .collect(Collectors.toList());
    }

    public boolean eliminaPrenotazione(PrenotazioneBean prenotazioneBean) {
        if (prenotazioneBean == null) return false;
        return prenotazioneDao.eliminaPrenotazione(String.valueOf(prenotazioneBean.getId()));
    }
}
