package com.example.mealcalendar.controller_applicativo;


import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.bean.DietBean;
import com.example.mealcalendar.bean.UserBean;
import com.example.mealcalendar.dao.UserDietDAO;
import com.example.mealcalendar.dao.UserDietDAOFactory;
import com.example.mealcalendar.model.DayEntity;
import com.example.mealcalendar.model.DietEntity;
import com.example.mealcalendar.model.MealEntity;
import com.example.mealcalendar.model.UserEntity;
import com.example.mealcalendar.register_login.LoginController;
import com.example.mealcalendar.register_login.UserLoginBean;

import java.io.IOException;

public class FollowDietController {

    private final LoginController logcontr = new LoginController();
    private UserDietDAO userdietDAO;
    private DietBean selecdiet;
    private UserBean userlog;

    public FollowDietController(DietBean selecdiet, UserBean user) {

        this.userdietDAO = UserDietDAOFactory.getuserdietDAO();
        this.selecdiet = selecdiet;
        this.userlog = user;

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


        user.setDietaAssegnata(diet);
        userdietDAO.saveUser(user);

    }

}
