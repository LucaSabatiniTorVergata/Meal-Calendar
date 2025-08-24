package com.example.mealcalendar.factory;

import com.example.mealcalendar.bean.RistoranteBean;
import com.example.mealcalendar.model.RistoranteEntity;

import java.util.List;
import java.util.stream.Collectors;

public class RistoranteFactory {

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
        if (entities == null) return null;
        return entities.stream()
                .map(RistoranteFactory::entityToBean)
                .collect(Collectors.toList());
    }
}
