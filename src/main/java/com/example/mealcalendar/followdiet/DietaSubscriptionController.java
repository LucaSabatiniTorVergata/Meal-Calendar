package com.example.mealcalendar.followdiet;

import com.example.mealcalendar.DietaCacheManagerSLT;
import com.example.mealcalendar.login.UserEntity;
import com.example.mealcalendar.makediet.DietaEntity;
import java.util.ArrayList;
import java.util.List;

public class DietaSubscriptionController {

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



}
