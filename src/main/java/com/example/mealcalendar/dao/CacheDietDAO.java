package com.example.mealcalendar.dao;

import com.example.mealcalendar.bean.DietBean;


import java.util.List;


//in teoria è un decorator

public class CacheDietDAO implements DietDAO {

    private final DietDAO backendDAO;  // può essere FileSystemDietDAO
    private final RamDietDAO cache = RamDietDAO.getInstance(); // usa il singleton

    public CacheDietDAO(DietDAO backendDAO) {
        this.backendDAO = backendDAO;
    }

    @Override
    public void saveDiet(String nutritionistUsername, DietBean diet) {
        diet.setNutritionistUsername(nutritionistUsername); // importante
        cache.saveDiet(nutritionistUsername, diet);
        backendDAO.saveDiet(nutritionistUsername, diet);
    }


    @Override
    public List<DietBean> getDietsByNutritionist(String nutritionistUsername) {

        List<DietBean> cached = cache.getDietsByNutritionist(nutritionistUsername);
        if (!cached.isEmpty()) return cached;

        List<DietBean> fromBackend = backendDAO.getDietsByNutritionist(nutritionistUsername);
        for (DietBean d : fromBackend) {
            cache.saveDiet(nutritionistUsername, d); // popola la cache
        }
        return fromBackend;
    }

    @Override
    public List<DietBean> getAllDiets() {

        List<DietBean> cached = cache.getAllDiets();
        if (!cached.isEmpty()) return cached;

        List<DietBean> allFromBackend = backendDAO.getAllDiets();
        for (DietBean d : allFromBackend) {
            String nutritionist = d.getNutritionistUsername();
            if (nutritionist != null) {
                cache.saveDiet(nutritionist, d);
            }
        }
        return allFromBackend;
    }

    public void clearAllCache() {
        cache.clearCache();
    }

}
