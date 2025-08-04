package com.example.mealcalendar.controller_applicativo;


import com.example.mealcalendar.dao.DietDAO;
import com.example.mealcalendar.dao.DietDAOFactory;
import com.example.mealcalendar.bean.DietBean;
import com.example.mealcalendar.SessionManagerSLT;

public class DietCreationController {

    private DietDAO dietDAO;

    public DietCreationController() {
        this.dietDAO = DietDAOFactory.getDietDAO();
    }

    public void saveDiet(DietBean diet) {
        String username = SessionManagerSLT.getInstance().getLoggedInUsername();
        dietDAO.saveDiet(username, diet);
    }
}
