package com.example.mealcalendar.makediet;

import java.util.ArrayList;
import java.util.List;

public class DietDaoDB implements DietDao {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mealcalendar";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = System.getenv("DBPASS");

    @Override
    public void save(DietaEntity dieta) {
     //we will see how to do it
    }

    @Override
    public List<DietaEntity> findAll(){

        return new ArrayList<>();
    }

    @Override
    public DietaEntity filbyname(String username){

        return null;
    }

}
