package com.example.mealcalendar.register_login;

import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.bean.RistoranteBean;
import com.example.mealcalendar.dao.RistoranteDao;
import com.example.mealcalendar.dao.UserDao;
import com.example.mealcalendar.factory.RistoranteFactory;
import com.example.mealcalendar.model.RistoranteEntity;

import java.util.List;
import java.util.stream.Collectors;

public class LoginController {

    private final UserDao userDao = new UserDao();
    private final RistoranteDao ristoranteDao = new RistoranteDao();

    public boolean vallogin(UserLoginBean userBean) {
        try {
            String ruolo = userBean.getRuolo();
            String username = userBean.getUsername();

            if ("restaurant".equalsIgnoreCase(ruolo)) {
                // Leggi gli entity e convertili in bean
                List<RistoranteBean> ristoranti = ristoranteDao.leggiRistoranti()
                        .stream()
                        .map(RistoranteFactory::entityToBean)
                        .collect(Collectors.toList());

                for (RistoranteBean r : ristoranti) {
                    if (r.getNome().equalsIgnoreCase(username)) {
                        SessionManagerSLT.getInstance().setLoggedInUsername(r.getNome());
                        SessionManagerSLT.getInstance().setLoggedRole(ruolo);
                        SessionManagerSLT.getInstance().setTipologiaRistorante(r.getTipologiaRistorante());
                        return true;
                    }
                }
            } else {
                List<UserBeanA> utenti = userDao.leggiUtenti();
                for (UserBeanA u : utenti) {
                    if (u.getUsername().equals(username)) {
                        SessionManagerSLT.getInstance().setLoggedInUsername(u.getUsername());
                        SessionManagerSLT.getInstance().setLoggedRole(u.getRuolo());
                        return true;
                    }
                }
            }

            return false;
        } catch (Exception e) {
            System.err.println("Errore durante il login: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
