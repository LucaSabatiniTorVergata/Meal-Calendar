package com.example.mealcalendar.dao;

import com.example.mealcalendar.SessionManagerSLT;

public class UserDietDAOFactory {


    public static UserDietDAO getuserdietDAO() {

        if (SessionManagerSLT.getInstance().getDemo()) {
            return RamUserDietDAO.getInstance();
        } else {
            if(SessionManagerSLT.getInstance().getFSDataBase()){
                return new CacheUserDietDAO(new FileSystemUserDietDAO());
            }else{
                //databasepersistenza
            }
        }
        return null;
    }
}