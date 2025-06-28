package com.example.mealcalendar.makediet;


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

            giorni.add(convertiGiorno(giornoBean));
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

    private GiornoDietaEntity convertiGiorno(GiornoBean bean) {
        return new GiornoDietaEntity(
                bean.getNgiorno(),
                convertiPasto(bean.getColazione()),
                convertiPasto(bean.getPranzo()),
                convertiPasto(bean.getCena())
        );
    }



}
