package com.example.mealcalendar.makediet;

import java.util.ArrayList;
import java.util.List;

public class DietDaoDB implements DietDao {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mealcalendar";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = System.getenv("DBPASS");

    @Override
    public void save(DietaEntity dieta) {

    }

    @Override
    public List<DietaEntity> findAll(){
        List<DietaEntity>diete=new ArrayList<>();
        return diete;
    }

    @Override
    public List<DietaEntity> Filbyauthor(String username){

        return List.of();
    }

}
