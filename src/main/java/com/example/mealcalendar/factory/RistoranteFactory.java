package com.example.mealcalendar.factory;

import com.example.mealcalendar.bean.RistoranteBean;
import com.example.mealcalendar.model.RistoranteEntity;

import java.util.Collections;
import java.util.List;

public final class RistoranteFactory {

    // Costruttore privato per impedire istanziazioni
    private RistoranteFactory() {}

    public static RistoranteEntity beanToEntity(RistoranteBean bean) {
        if (bean == null) return null;
        RistoranteEntity entity = new RistoranteEntity();
        entity.setNome(bean.getNome());
        entity.setIndirizzo(bean.getIndirizzo());
        entity.setPostiDisponibili(bean.getPostiDisponibili());
        entity.setTipologiaRistorante(bean.getTipologiaRistorante());
        return entity;
    }

    public static RistoranteBean entityToBean(RistoranteEntity entity) {
        if (entity == null) return null;
        return new RistoranteBean(
                entity.getNome(),
                entity.getIndirizzo(),
                entity.getPostiDisponibili(),
                entity.getTipologiaRistorante()
        );
    }

    public static List<RistoranteBean> entitiesToBeans(List<RistoranteEntity> entities) {
        if (entities == null || entities.isEmpty()) return Collections.emptyList();
        return entities.stream()
                .map(RistoranteFactory::entityToBean)
                .toList(); // lista immutabile moderna
    }
}
