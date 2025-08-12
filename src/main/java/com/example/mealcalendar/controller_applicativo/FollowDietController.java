package com.example.mealcalendar.controller_applicativo;


import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.bean.*;
import com.example.mealcalendar.dao.DietDAO;
import com.example.mealcalendar.dao.DietTakenDAO;
import com.example.mealcalendar.dao.UserDietDAO;
import com.example.mealcalendar.model.*;
import com.example.mealcalendar.register_login.LoginController;
import com.example.mealcalendar.register_login.UserLoginBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FollowDietController {

    private final LoginController logcontr = new LoginController();
    private DietBean selecdiet;
    private UserBean userlog;

    public FollowDietController(DietBean selecdiet, UserBean user) {

        this.selecdiet = selecdiet;
        this.userlog = user;

    }

    public FollowDietController(){
    }

    public void assignDiet() throws IOException {

        UserLoginBean logus=new UserLoginBean(userlog.getNome(), SessionManagerSLT.getInstance().getLoggedpassword(), userlog.getRuolo());

        if(logcontr.vallogin(logus))

        {
            // Conversione da Bean ad Entity
            UserEntity user = new UserEntity(
                    userlog.getNome(),
                    userlog.getEmail(),
                    userlog.getRuolo()
            );

            DietEntity diet = new DietEntity(
                    selecdiet.getNome(),
                    selecdiet.getDescrizione(),
                    selecdiet.getDurata(),
                    selecdiet.getNutritionistUsername()
            );

            selecdiet.getGiorni().forEach(dayBean -> {
                DayEntity day = new DayEntity(dayBean.getGiorno());
                dayBean.getPasti().forEach(mealBean -> {
                    MealEntity meal = new MealEntity(
                            mealBean.getNome(),
                            mealBean.getDescrizione(),
                            mealBean.getKcal()
                    );
                    day.addMeal(meal);
                });
                diet.addDay(day);
            });

            saveUserDiet(user, diet);
        }

    }

    public void saveUserDiet(UserEntity user, DietEntity diet) {

        if(user.getDietaAssegnata()==null){
        user.setDietaAssegnata(diet);
        UserDietDAO.getInstance().saveUser(user);
        UserDietDAO.getInstance().getAllUsers();

    }
    }

    public DietBean getAssignedDiet(String mail){

        UserEntity user = UserDietDAO.getInstance().getUserByEmail(mail);

        if (user != null && user.getDietaAssegnata() != null) {
            DietEntity diet = user.getDietaAssegnata();
            DietBean bean = new DietBean();
            bean.setNome(diet.getNome());
            bean.setDescrizione(diet.getDescrizione());
            bean.setDurata(diet.getDurata());
            bean.setNutritionistUsername(diet.getNutritionistUsername());

            diet.getGiorni().forEach(dayEntity -> {
                DayBean day = new DayBean();
                day.setGiorno(dayEntity.getGiorno());
                dayEntity.getPasti().forEach(mealEntity -> {
                    MealBean meal = new MealBean();
                    meal.setNome(mealEntity.getNome());
                    meal.setDescrizione(mealEntity.getDescrizione());
                    meal.setKcal(mealEntity.getKcal());
                    day.addMeal(meal);
                });
                bean.addDay(day);
            });
            return bean;
        }
        return null;
    }

    public List<DietBean> convertdiet(){

        List<DietBean> dietB= new ArrayList<>();
        List<DietEntity> dietE=new ArrayList<>();

        dietE.addAll(DietDAO.getInstance().getRamStorage());
        
        for (DietEntity entity : dietE) {
            
         DietBean bean = new DietBean();
         bean.setNome(entity.getNome());
         bean.setDescrizione(entity.getDescrizione());
         bean.setDurata(entity.getDurata());
         bean.setNutritionistUsername(entity.getNutritionistUsername());
         List<DayBean> dayBeans = new ArrayList<>();

         for (DayEntity dayEntity : entity.getGiorni()) {

             DayBean dayBean = new DayBean();
             dayBean.setGiorno(dayEntity.getGiorno());

             List<MealBean> mealBeans = new ArrayList<>();
             for (MealEntity mealEntity : dayEntity.getPasti()) {

                 MealBean mealBean = new MealBean();
                 mealBean.setNome(mealEntity.getNome());
                 mealBean.setDescrizione(mealEntity.getDescrizione());
                 mealBean.setKcal(mealEntity.getKcal());
                 mealBeans.add(mealBean);
             }

             dayBean.setPasti(mealBeans);
             dayBeans.add(dayBean);
         }

         bean.setGiorni(dayBeans);
         dietB.add(bean);
        }
        return dietB;
    }

    public void insertmeal(DietTakenBean bean){

        DietTakenDAO.getInstance().insertDiet(converterTaken( bean));
        DietTakenDAO.getInstance().getAll();

    }

    public TakenDietEntity converterTaken(DietTakenBean bean){

        TakenDietEntity dieta = new TakenDietEntity(
                bean.getUser()
        );

        bean.getDietTaken().forEach(dayBean -> {
            TakenDayEntity day = new TakenDayEntity(dayBean.getGiorno());
            dayBean.getMealsTaken().forEach(mealBean -> {
                TakenMealEntity meal = new TakenMealEntity(
                        mealBean.getNome(),
                        mealBean.getDescrizione(),
                        mealBean.getKcal()
                );
                day.addMeal(meal);
            });
            dieta.addDay(day);
        });
        return dieta;

    }

    public List<String> generaResoconto() {



        UserEntity currentUser= UserDietDAO.getInstance()
                .getramStorage()
                .stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(SessionManagerSLT.getInstance().getLoggedmail()))
                .findFirst()
                .orElse(null);

        DietEntity dieta = currentUser.getDietaAssegnata();

        TakenDietEntity dietaAssunta = DietTakenDAO.getInstance()
                .getRamStorage()
                .stream()
                .filter(d -> d.getUser().equalsIgnoreCase(SessionManagerSLT.getInstance().getLoggedInUsername()))
                .findFirst()
                .orElse(null);

        List<String> risultati = new ArrayList<>();

        if (dieta == null || dietaAssunta == null) {
            risultati.add("Dieta o pasti assunti non trovati.");
            return risultati;
        }

        List<DayEntity> giorniPrevisti = dieta.getGiorni();
        List<TakenDayEntity> giorniAssunti = dietaAssunta.getDays();

        for (int i = 0; i < giorniPrevisti.size(); i++) {
            risultati.add(generaResocontoGiornaliero(i, giorniPrevisti.get(i), giorniAssunti.size() > i ? giorniAssunti.get(i) : null));
        }

        return risultati;
    }

    private String generaResocontoGiornaliero(int giornoIndex, DayEntity previsto, TakenDayEntity assunto) {
        StringBuilder sb = new StringBuilder();
        sb.append("Giorno ").append(giornoIndex + 1).append(":\n");

        int kcalPrevisto = previsto.getPasti().stream().mapToInt(MealEntity::getKcal).sum();
        int kcalAssunto = (assunto != null)
                ? assunto.getPasti().stream().mapToInt(TakenMealEntity::getKcal).sum()
                : 0;

        if (assunto == null) {
            sb.append("  Nessun pasto inserito.\n");
        } else {
            sb.append(generaDettagliPasti(previsto, assunto));
        }

        sb.append("  Totale kcal previsto: ").append(kcalPrevisto).append("\n");
        sb.append("  Totale kcal assunto: ").append(kcalAssunto).append("\n");
        sb.append(valutaDifferenzaKcal(kcalPrevisto, kcalAssunto));

        return sb.toString();
    }

    private String generaDettagliPasti(DayEntity previsto, TakenDayEntity assunto) {
        StringBuilder sb = new StringBuilder();

        for (int j = 0; j < previsto.getPasti().size(); j++) {
            MealEntity pPrev = previsto.getPasti().get(j);
            TakenMealEntity pAssunto = (assunto.getPasti().size() > j) ? assunto.getPasti().get(j) : null;

            sb.append("  - Pasto ").append(j + 1)
                    .append(": previsto [").append(pPrev.getNome()).append(", ").append(pPrev.getKcal()).append(" kcal]");

            if (pAssunto != null) {
                sb.append(" | assunto [").append(pAssunto.getNome()).append(", ").append(pAssunto.getKcal()).append(" kcal]");
            } else {
                sb.append(" | assunto: nessun dato");
            }

            sb.append("\n");
        }

        return sb.toString();
    }

    private String valutaDifferenzaKcal(int previsto, int assunto) {
        if (assunto > previsto) {
            return "  ⚠ Hai superato le calorie previste.\n";
        } else if (assunto < previsto) {
            return "  ⚠ Hai assunto meno calorie del previsto.\n";
        } else {
            return "  ✅ Calorie rispettate.\n";
        }
    }

    public void delete(String username,String mail){

        UserDietDAO.getInstance().deleteByUserEmail(mail);
        DietTakenDAO.getInstance().deleteByUser(username);

    }

    public Boolean requnutr(){

        if(Boolean.TRUE.equals(SessionManagerSLT.getInstance().getRequestnutr())){

            UserEntity currentUser= UserDietDAO.getInstance()
                    .getramStorage()
                    .stream()
                    .filter(user -> user.getEmail().equalsIgnoreCase(SessionManagerSLT.getInstance().getLoggedmail()))
                    .findFirst()
                    .orElse(null);

            String nutrname=currentUser.getDietaAssegnata().getNutritionistUsername();

            RequestNutritionsReportController controller= new RequestNutritionsReportController();
            controller.askreport(nutrname);
            SessionManagerSLT.getInstance().setRequestnutr(false);
            return true;
        }
        return false;

    }



}


