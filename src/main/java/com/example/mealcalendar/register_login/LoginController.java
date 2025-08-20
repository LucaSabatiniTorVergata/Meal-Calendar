package com.example.mealcalendar.register_login;

import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.bean.RistoranteBean;
import com.example.mealcalendar.dao.RistoranteDao;
import com.example.mealcalendar.dao.UserDao;

import java.io.IOException;
import java.util.List;

public class LoginController {

    private final UserDao userDao = new UserDao();
    private final RistoranteDao ristoranteDao = new RistoranteDao();

    public boolean vallogin(UserLoginBean userBean) throws IOException {

        if ("restaurant".equalsIgnoreCase(userBean.getRuolo())) {
            List<RistoranteBean> ristoranti = ristoranteDao.leggiRistoranti();
            for (RistoranteBean r : ristoranti) {
                if (r.getUsername().equals(userBean.getUsername()) &&
                        r.getPassword().equals(userBean.getPassword()) &&
                        r.getRuolo().equals(userBean.getRuolo())) {

                    SessionManagerSLT.getInstance().setLoggedInUsername(r.getUsername());
                    SessionManagerSLT.getInstance().setLoggedRole(r.getRuolo());
                    SessionManagerSLT.getInstance().setLoggedmail(r.getEmail());
                    SessionManagerSLT.getInstance().setLoggedpassword(r.getPassword());
                    SessionManagerSLT.getInstance().setTipologiaRistorante(r.getTipologiaRistorante());
                    return true;
                }
            }
        } else {
            List<UserBeanA> utenti = userDao.leggiUtenti();
            for (UserBeanA u : utenti) {
                if (u.getUsername().equals(userBean.getUsername()) &&
                        u.getPassword().equals(userBean.getPassword()) &&
                        u.getRuolo().equals(userBean.getRuolo())) {

                    SessionManagerSLT.getInstance().setLoggedInUsername(u.getUsername());
                    SessionManagerSLT.getInstance().setLoggedRole(u.getRuolo());
                    SessionManagerSLT.getInstance().setLoggedmail(u.getEmail());
                    SessionManagerSLT.getInstance().setLoggedpassword(u.getPassword());
                    return true;
                }
            }
        }

        return false;
    }
}
