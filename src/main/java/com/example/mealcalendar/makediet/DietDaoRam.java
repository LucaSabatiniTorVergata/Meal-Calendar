package com.example.mealcalendar.makediet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DietDaoRam implements DietDao {

    private final Map<String, DietaEntity> dietaStorage = new HashMap<>();

    @Override
    public void save(DietaEntity dieta) {

        dietaStorage.put(dieta.getNome(), dieta);
    }

    @Override
    public List<DietaEntity> findAll(){
        return new ArrayList<>(dietaStorage.values());
    }

    @Override
    public DietaEntity filbyname(String username){
        return dietaStorage.values()
                .stream()
                .filter(d -> d.getAutore().equals(username))
                .findFirst()
                .orElse(null);
    }

}


