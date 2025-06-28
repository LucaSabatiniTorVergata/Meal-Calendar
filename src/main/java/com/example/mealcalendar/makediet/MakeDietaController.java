package com.example.mealcalendar.makediet;

import com.example.mealcalendar.login.UserDaoFactory;
import com.example.mealcalendar.login.UserDaoInterface;

import java.util.ArrayList;
import java.util.List;

public class MakeDietaController {

    private DietDao dietDao;


    public MakeDietaController() {
        this.dietDao= DietDaoFactory.createDietDao();
    }

    public boolean saveDieta(DietaBean dieta) {

        if(dieta!=null) {

        List<GiornoDietaEntity> giorni = new ArrayList<>();
        for (GiornoBean giornoBean : dieta.getGiorni()) {

            PastoEntity colazione = convertiPasto(giornoBean.getColazione());
            PastoEntity pranzo = convertiPasto(giornoBean.getPranzo());
            PastoEntity cena = convertiPasto(giornoBean.getCena());

            GiornoDietaEntity giorno = new GiornoDietaEntity(
                    giornoBean.getNgiorno(),
                    colazione,
                    pranzo,
                    cena
            );

            giorni.add(giorno);
        }

        DietaEntity dieta1=new DietaEntity(dieta.getNome(),dieta.getDescrizione(),dieta.getAutore(),giorni,dieta.getDataPubblicazione(),dieta.getDurata());

        dietDao.save(dieta1);
        return true;
        }
        else{
            return false;
        }

    }
    private PastoEntity convertiPasto(PastoBean bean) {
        return new PastoEntity(bean.getNomePiatto(), bean.getCalorie());
    }



}
