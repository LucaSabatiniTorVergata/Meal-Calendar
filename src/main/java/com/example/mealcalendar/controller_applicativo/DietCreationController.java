package com.example.mealcalendar.controller_applicativo;


import com.example.mealcalendar.dao.DietDAO;
import com.example.mealcalendar.bean.DietBean;
import com.example.mealcalendar.handlexceptions.DietNotFoundException;
import com.example.mealcalendar.handlexceptions.DietPersistenceException;
import com.example.mealcalendar.model.DayEntity;
import com.example.mealcalendar.model.DietEntity;
import com.example.mealcalendar.model.MealEntity;

import java.util.logging.Logger;

public class DietCreationController {

    private static final Logger logger = Logger.getLogger(DietCreationController.class.getName());

    public void saveDiet(DietBean diet) {

        try {
            DietDAO.getInstance().saveDiet(converter(diet));
            DietDAO.getInstance().getAllDiets();
            logger.info("Dieta salvata con successo!");
        } catch (DietPersistenceException e) {
            logger.info("Errore di persistenza: " + e.getMessage());
        } catch (DietNotFoundException e) {
            logger.info("Errore: " + e.getMessage());
        }

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
