package com.example.mealcalendar.controller_applicativo;


import com.example.mealcalendar.dao.DietDAO;
import com.example.mealcalendar.bean.DietBean;
import com.example.mealcalendar.model.DayEntity;
import com.example.mealcalendar.model.DietEntity;
import com.example.mealcalendar.model.MealEntity;

public class DietCreationController {



    public void saveDiet(DietBean diet) {

        DietDAO.getInstance().saveDiet(converter(diet));
        DietDAO.getInstance().getAllDiets();
    }

    public DietEntity converter(DietBean diet) {

        DietEntity dieta = new DietEntity(
                diet.getNome(),
                diet.getDescrizione(),
                diet.getDurata(),
                diet.getNutritionistUsername()
        );

        diet.getGiorni().forEach(dayBean -> {
            DayEntity day = new DayEntity(dayBean.getGiorno());
            dayBean.getPasti().forEach(mealBean -> {
                MealEntity meal = new MealEntity(
                        mealBean.getNome(),
                        mealBean.getDescrizione(),
                        mealBean.getKcal()
                );
                day.addMeal(meal);
            });
            dieta.addDay(day);
        });
        return dieta;

    }

}
