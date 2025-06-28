package com.example.mealcalendar.makediet;

import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.login.UserDaoDB;
import com.example.mealcalendar.login.UserDaoFS;
import com.example.mealcalendar.login.UserDaoInterface;
import com.example.mealcalendar.login.UserDaoRam;

public class DietDaoFactory {

    private DietDaoFactory() {}

    public static DietDao createDietDao() {
        SessionManagerSLT session = SessionManagerSLT.getInstance();

        if (session.getDemo()) {
            return new DietDaoRam(); // in-memory
        } else if (session.getFSDataBase()) {
            return new DietDaoDB(); // database
        } else {
            return new DietDaoFS(); // filesystem
        }
    }
}
