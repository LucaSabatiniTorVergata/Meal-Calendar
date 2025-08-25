package com.example.mealcalendar.register_login;

import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.bean.RistoranteBean;
import com.example.mealcalendar.dao.RistoranteDao;
import com.example.mealcalendar.dao.UserDao;
import com.example.mealcalendar.exception.LoginException;
import com.example.mealcalendar.factory.RistoranteFactory;
import com.example.mealcalendar.model.RistoranteEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginController {

    private final UserDao userDao = new UserDao();
    private final RistoranteDao ristoranteDao = new RistoranteDao();

    public boolean vallogin(UserLoginBean userBean) throws LoginException {
        String ruolo = userBean.getRuolo();
        String username = userBean.getUsername();

        if ("restaurant".equalsIgnoreCase(ruolo)) {
            return loginRistorante(username, ruolo);
        } else {
            return loginUtente(username);
        }
    }


    private boolean loginRistorante(String username, String ruolo) throws LoginException {
        List<RistoranteEntity> entities = new ArrayList<>(ristoranteDao.leggiRistoranti());

        if (isRamAndEmpty(entities)) {
            throw new LoginException("Nessun ristorante presente in RAM. Impossibile fare il login.");
        }

        List<RistoranteBean> ristoranti = entities.stream()
                .map(RistoranteFactory::entityToBean)
                .toList();

        for (RistoranteBean r : ristoranti) {
            if (r.getNome().equalsIgnoreCase(username)) {
                SessionManagerSLT.getInstance().setLoggedInUsername(r.getNome());
                SessionManagerSLT.getInstance().setLoggedRole(ruolo);
                SessionManagerSLT.getInstance().setTipologiaRistorante(r.getTipologiaRistorante());
                return true;
            }
        }

        throw new LoginException("Ristorante con username " + username + " non trovato.");
    }


    private boolean loginUtente(String username) throws LoginException {
        List<UserBeanA> utenti = userDao.leggiUtenti();

        if (utenti.isEmpty()) {
            throw new LoginException("Nessun utente registrato.");
        }

        for (UserBeanA u : utenti) {
            if (u.getUsername().equals(username)) {
                SessionManagerSLT.getInstance().setLoggedInUsername(u.getUsername());
                SessionManagerSLT.getInstance().setLoggedRole(u.getRuolo());
                return true;
            }
        }

        throw new LoginException("Utente " + username + " non trovato.");
    }

    private boolean isRamAndEmpty(List<RistoranteEntity> entities) {
        return SessionManagerSLT.getInstance().getPersistenceType() != null &&
                "RAM".equals(SessionManagerSLT.getInstance().getPersistenceType().name()) &&
                entities.isEmpty();
    }
}
