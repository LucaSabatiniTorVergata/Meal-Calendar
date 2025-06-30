package com.example.mealcalendar.followdiet;

import com.example.mealcalendar.login.UserSessionCacheSLT;
import com.example.mealcalendar.makediet.DietaCacheManagerSLT;
import com.example.mealcalendar.login.UserDaoFactory;
import com.example.mealcalendar.login.UserDaoInterface;
import com.example.mealcalendar.login.UserEntity;
import com.example.mealcalendar.makediet.DietDao;
import com.example.mealcalendar.makediet.DietDaoFactory;
import com.example.mealcalendar.makediet.DietaEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DietaSubscriptionController {

    private UserDaoInterface userDAO;
    private DietDao dietDao;

    public DietaSubscriptionController() {
        this.userDAO= UserDaoFactory.createUserDao();
        this.dietDao= DietDaoFactory.createDietDao();
    }


    public List<DietaVisualBean> getDieteDisponibili() {
        List<DietaEntity> diete = DietaCacheManagerSLT.getInstance().getDiete(); // usa la cache
        List<DietaVisualBean> result = new ArrayList<>();

        for (DietaEntity d : diete) {
            DietaVisualBean bean = new DietaVisualBean();
            bean.setNome(d.getNome());
            bean.setDescrizione(d.getDescrizione());
            bean.setAutore(d.getAutore());
            bean.setDurata(d.getDurataSettimane());
            result.add(bean);
        }

        return result;
    }

    public void assegnaDietaAUser(DietaVisualBean bean, String username) throws IOException {
        DietaEntity dieta = dietDao.filbyname(bean.getNome());
        if (dieta != null) {
            UserEntity user = UserSessionCacheSLT.getInstance().getUser(username);
            user.setDietaAssegnata(dieta);
            userDAO.updateUser(user);
            UserSessionCacheSLT.getInstance().invalidateAll();
        }
    }

}
