package com.example.mealcalendar.dao;

import com.example.mealcalendar.SessionManagerSLT;

public class DietDAOFactory {

    public static DietDAO getDietDAO() {

        if (SessionManagerSLT.getInstance().getDemo()) {
            return RamDietDao.getInstance();
        } else {
            if(SessionManagerSLT.getInstance().getFSDataBase()){
                return new CacheDietDAO(new FileSystemDietDAO());
            }else{
                //databasepersistenza
                }
            }
        return null;
    }
}





