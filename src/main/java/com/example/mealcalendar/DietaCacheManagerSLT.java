package com.example.mealcalendar;

import com.example.mealcalendar.makediet.DietDao;
import com.example.mealcalendar.makediet.DietDaoFactory;
import com.example.mealcalendar.makediet.DietaEntity;

import java.util.List;

public class DietaCacheManagerSLT {

    private static DietaCacheManagerSLT instance;

    private List<DietaEntity> cache;

    private DietaCacheManagerSLT() {}

    public static synchronized DietaCacheManagerSLT getInstance() {
        if (instance == null) {
            instance = new DietaCacheManagerSLT();
        }
        return instance;
    }

    public List<DietaEntity> getDiete() {
        if (cache == null) {
            // Carica dal DAO (DB, file, ecc.)
            DietDao dao = DietDaoFactory.createDietDao();
            cache = dao.findAll();
        }
        return cache;
    }

    public void invalidateCache() {
        cache = null;
    }

    public void addDieta(DietaEntity d) {
        if (cache != null) {
            cache.add(d);
        }
    }
}