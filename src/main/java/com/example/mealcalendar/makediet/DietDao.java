package com.example.mealcalendar.makediet;

import java.util.List;

public interface DietDao {
    void save(DietaEntity dieta);
    List<DietaEntity> findAll();
    DietaEntity filbyname(String name);
}
