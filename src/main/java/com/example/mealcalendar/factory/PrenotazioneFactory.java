package com.example.mealcalendar.factory;

import com.example.mealcalendar.bean.PrenotazioneBean;
import com.example.mealcalendar.model.PrenotazioneEntity;

public class PrenotazioneFactory {

    public static PrenotazioneEntity beanToEntity(PrenotazioneBean bean) {
        if (bean == null) return null;
        PrenotazioneEntity entity = new PrenotazioneEntity();
        entity.setId(bean.getId());
        entity.setDataPrenotazione(bean.getDataPrenotazione());
        entity.setDataScadenza(bean.getDataScadenza());
        entity.setOraPrenotazione(bean.getOraPrenotazione());
        entity.setUsernameUtente(bean.getUsernameUtente());
        entity.setNomeRistorante(bean.getNomeRistorante());
        entity.setPostiASedere(bean.getPostiASedere());
        entity.setScaduta(bean.isScaduta());
        return entity;
    }

    public static PrenotazioneBean entityToBean(PrenotazioneEntity entity) {
        if (entity == null) return null;
        PrenotazioneBean bean = new PrenotazioneBean(
                entity.getId(),
                entity.getDataPrenotazione(),
                entity.getDataScadenza(),
                entity.getOraPrenotazione(),
                entity.getUsernameUtente(),
                entity.getNomeRistorante(),
                entity.getPostiASedere()
        );
        bean.setScaduta(entity.isScaduta());
        return bean;
    }
}
