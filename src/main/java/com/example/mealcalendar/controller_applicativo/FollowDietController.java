package com.example.mealcalendar.controller_applicativo;


import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.bean.*;
import com.example.mealcalendar.dao.DietDAO;
import com.example.mealcalendar.dao.DietTakenDAO;
import com.example.mealcalendar.dao.UserDietDAO;
import com.example.mealcalendar.model.*;
import com.example.mealcalendar.register_login.LoginController;
import com.example.mealcalendar.register_login.UserLoginBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FollowDietController {

    private final LoginController logcontr = new LoginController();
    private DietBean selecdiet;
    private UserBean userlog;

    public FollowDietController(DietBean selecdiet, UserBean user) {

        this.selecdiet = selecdiet;
        this.userlog = user;

    }

    public FollowDietController(){
    }

    public void assignDiet() throws IOException {

        UserLoginBean logus=new UserLoginBean(userlog.getNome(), SessionManagerSLT.getInstance().getLoggedpassword(), userlog.getRuolo());

        if(logcontr.vallogin(logus))

        {
            // Conversione da Bean ad Entity
            UserEntity user = new UserEntity(
                    userlog.getNome(),
                    userlog.getEmail(),
                    userlog.getRuolo()
            );

            DietEntity diet = new DietEntity(
                    selecdiet.getNome(),
                    selecdiet.getDescrizione(),
                    selecdiet.getDurata(),
                    selecdiet.getNutritionistUsername()
            );

            selecdiet.getGiorni().forEach(dayBean -> {
                DayEntity day = new DayEntity(dayBean.getGiorno());
                dayBean.getPasti().forEach(mealBean -> {
                    MealEntity meal = new MealEntity(
                            mealBean.getNome(),
                            mealBean.getDescrizione(),
                            mealBean.getKcal()
                    );
                    day.addMeal(meal);
                });
                diet.addDay(day);
            });

            saveUserDiet(user, diet);
        }

    }

    public void saveUserDiet(UserEntity user, DietEntity diet) {

        if(user.getDietaAssegnata()==null){
        user.setDietaAssegnata(diet);
        UserDietDAO.getInstance().saveUser(user);
        UserDietDAO.getInstance().getAllUsers();

    }
    }

    public DietBean getAssignedDiet(String mail){

        UserEntity user = UserDietDAO.getInstance().getUserByEmail(mail);

        if (user != null && user.getDietaAssegnata() != null) {
            DietEntity diet = user.getDietaAssegnata();
            DietBean bean = new DietBean();
            bean.setNome(diet.getNome());
            bean.setDescrizione(diet.getDescrizione());
            bean.setDurata(diet.getDurata());
            bean.setNutritionistUsername(diet.getNutritionistUsername());

            diet.getGiorni().forEach(dayEntity -> {
                DayBean day = new DayBean();
                day.setGiorno(dayEntity.getGiorno());
                dayEntity.getPasti().forEach(mealEntity -> {
                    MealBean meal = new MealBean();
                    meal.setNome(mealEntity.getNome());
                    meal.setDescrizione(mealEntity.getDescrizione());
                    meal.setKcal(mealEntity.getKcal());
                    day.addMeal(meal);
                });
                bean.addDay(day);
            });
            return bean;
        }
        return null;
    }

    public List<DietBean> convertdiet(){

        List<DietBean> dietB= new ArrayList<>();
        List<DietEntity> dietE=new ArrayList<>();
        dietE.addAll(DietDAO.getInstance().getRamStorage());
        
        for (DietEntity entity : dietE) {
            
         DietBean bean = new DietBean();
         bean.setNome(entity.getNome());
         bean.setDescrizione(entity.getDescrizione());
         bean.setDurata(entity.getDurata());
         bean.setNutritionistUsername(entity.getNutritionistUsername());
         List<DayBean> dayBeans = new ArrayList<>();

         for (DayEntity dayEntity : entity.getGiorni()) {

             DayBean dayBean = new DayBean();
             dayBean.setGiorno(dayEntity.getGiorno());

             List<MealBean> mealBeans = new ArrayList<>();
             for (MealEntity mealEntity : dayEntity.getPasti()) {

                 MealBean mealBean = new MealBean();
                 mealBean.setNome(mealEntity.getNome());
                 mealBean.setDescrizione(mealEntity.getDescrizione());
                 mealBean.setKcal(mealEntity.getKcal());
                 mealBeans.add(mealBean);
             }

             dayBean.setPasti(mealBeans);
             dayBeans.add(dayBean);
         }

         bean.setGiorni(dayBeans);
         dietB.add(bean);
        }
        return dietB;
    }

    public void insertmeal(DietTakenBean bean){

        DietTakenDAO.getInstance().insertDiet(converterTaken( bean));
        DietTakenDAO.getInstance().getAll();

    }

    public TakenDietEntity converterTaken(DietTakenBean bean){

        TakenDietEntity dieta = new TakenDietEntity(
                bean.getUser()
        );

        bean.getDietTaken().forEach(dayBean -> {
            TakenDayEntity day = new TakenDayEntity(dayBean.getGiorno());
            dayBean.getMealsTaken().forEach(mealBean -> {
                TakenMealEntity meal = new TakenMealEntity(
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


