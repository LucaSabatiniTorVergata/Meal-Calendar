package com.example.mealcalendar.makediet;

import java.util.List;

public interface DietDao {
    void save(DietaEntity dieta);
    List<DietaEntity> findAll();
    List<DietaEntity> Filbyauthor(String author);
}
