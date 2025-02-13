package com.example.mealcalendar;

import java.util.List;

public class MealcalendarController {

    private MealcalendarBean mealcalendarBean;

    public MealcalendarController(MealcalendarBean bean){
        this.mealcalendarBean = bean;

    }

    public String getMail()throws Exception {
       UserDao dao=new UserDao(false);
       UserEntity user=dao.getUserByUsername(mealcalendarBean.getUser());
       return user.getEmail();
    }

    public void invioMail()throws Exception {
        String mail=getMail();


    }
}
