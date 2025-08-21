package com.example.mealcalendar.register_login;

import com.example.mealcalendar.bean.RistoranteBean;
import com.example.mealcalendar.dao.RistoranteDao;
import com.example.mealcalendar.dao.UserDao;
import com.example.mealcalendar.model.TipologiaRistorante;

import java.io.IOException;

public class RegistrationController {

    private final UserDao userDao = new UserDao();
    private final RistoranteDao ristoranteDao = new RistoranteDao();

    // Registrazione utente normale
    public void register(UserBeanA bean) throws IOException {
        userDao.saveUser(bean);
    }

    // Registrazione ristorante
    public void registerRistorante(RistoranteBean ristoranteBean) throws IOException {
        ristoranteDao.aggiungiRistorante(ristoranteBean);
    }
}
