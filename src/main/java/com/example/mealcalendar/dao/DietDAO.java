package com.example.mealcalendar.dao;

import com.example.mealcalendar.bean.DietBean;

import java.util.List;

public interface DietDAO {

    void saveDiet(String nutritionistUsername, DietBean diet);
    List<DietBean> getDietsByNutritionist(String nutritionistUsername);
    List<DietBean> getAllDiets();

}

