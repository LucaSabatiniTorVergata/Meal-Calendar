package com.example.mealcalendar.makediet;


import java.util.ArrayList;
import java.util.List;

public class MakeDietaController {

    private DietDao dietDao;


    public MakeDietaController() {
        this.dietDao= DietDaoFactory.createDietDao();
    }

    public boolean saveDieta(DietaBean dieta) {

        if(dieta!=null && dietDao.filbyname(dieta.getNome())==null ) {

         dietDao.save(convertiDieta(dieta));
         DietaCacheManagerSLT.getInstance().invalidateCache();
         return true;
        }
        else{
            return false;
        }

    }

    private DietaEntity convertiDieta(DietaBean dieta) {

            List<GiornoDietaEntity> giorni = new ArrayList<>();
            for (GiornoBean giornoBean : dieta.getGiorni()) {

                giorni.add(convertiGiorno(giornoBean));
            }

            return new DietaEntity(dieta.getNome(),dieta.getDescrizione(),dieta.getAutore(),giorni,dieta.getDataPubblicazione(),dieta.getDurata());

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
