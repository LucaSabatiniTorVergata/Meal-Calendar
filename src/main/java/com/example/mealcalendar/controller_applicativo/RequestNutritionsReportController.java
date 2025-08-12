package com.example.mealcalendar.controller_applicativo;

import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.UserWithNutritionistRole;
import com.example.mealcalendar.UserWithReportRequest;
import com.example.mealcalendar.bean.*;
import com.example.mealcalendar.dao.DietTakenDAO;
import com.example.mealcalendar.dao.ReportRequestDAO;
import com.example.mealcalendar.dao.UserDietDAO;
import com.example.mealcalendar.model.*;
import com.example.mealcalendar.patternobserver.ReportObserver;
import com.example.mealcalendar.patternobserver.Subject;

import java.util.Comparator;
import java.util.List;

public class RequestNutritionsReportController implements Subject{

    private ReportObserver observer;

    public void askreport(String nutritionist) {

        UserEntity baseUser= UserDietDAO.getInstance()
                .getramStorage()
                .stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(SessionManagerSLT.getInstance().getLoggedmail()))
                .findFirst()
                .orElse(null);

        TakenDietEntity taken = DietTakenDAO.getInstance()
                .getRamStorage()
                .stream()
                .filter(d -> d.getUser().equalsIgnoreCase(SessionManagerSLT.getInstance().getLoggedInUsername()))
                .findFirst()
                .orElse(null);

        DietEntity assigned = baseUser.getDietaAssegnata();

        if (assigned == null || taken == null) {
            throw new IllegalStateException("Dieta o pasti assunti non trovati.");
        }

        UserWithReportRequest decorated = new UserWithReportRequest(baseUser);
        decorated.sendReportRequest(taken, assigned, nutritionist);
    }
    /**
         * Recupera la lista di richieste pendenti come bean per il nutrizionista loggato.
         */
        public List<ReportRequestBean> getPendingRequestsForNutritionist() {

            String userEmail = SessionManagerSLT.getInstance().getLoggedmail();
            UserEntity baseUser = new UserEntity(SessionManagerSLT.getInstance().getLoggedInUsername(), userEmail,SessionManagerSLT.getInstance().getLoggedRole());
            baseUser.setDietaAssegnata(null);


            // Decorator
            UserWithNutritionistRole nutritionist = new UserWithNutritionistRole(baseUser);

            // Prendo tutte le richieste pendenti (Entity)
            List<ReportRequestEntity> pendingEntities = nutritionist.getPendingReportRequests();


            // Converto in bean per passare alla view
            return pendingEntities.stream()
                    .map(this::convertEntityToBean)
                    .toList();
        }

        /**
         * Permette di rispondere ad una richiesta specifica (riceve bean)
         */
        public void updateResponse(ReportRequestBean bean) {

            UserEntity baseUser = new UserEntity(SessionManagerSLT.getInstance().getLoggedInUsername(),SessionManagerSLT.getInstance().getLoggedmail(), SessionManagerSLT.getInstance().getLoggedRole());

            // Carica l'entità originale (se hai un id usa quello, qui filtro per user + nutritionist + data)
            List<ReportRequestEntity> allRequests = ReportRequestDAO.getInstance().getAllByNutritionist(bean.getNutritionistEmail());
            ReportRequestEntity entity = allRequests.stream()
                    .filter(r -> r.getUserEmail().equals(bean.getUserEmail()) &&
                            r.getRequestTime().equals(bean.getRequestDate()))
                    .findFirst()
                    .orElse(null);

            if (entity != null) {
                UserWithNutritionistRole nutritionist = new UserWithNutritionistRole(baseUser);
                nutritionist.respondToReportRequest(entity,bean.getResponse());
            }

        }

        private ReportRequestBean convertEntityToBean(ReportRequestEntity entity) {
            ReportRequestBean bean = new ReportRequestBean();
            bean.setUserEmail(entity.getDietTaken().getUser());
            bean.setNutritionistEmail(entity.getNutritionistEmail());
            bean.setDietName(entity.getDietAssigned() != null ? entity.getDietAssigned().getNome() : "");
            bean.setDietDescription(entity.getDietAssigned() != null ? entity.getDietAssigned().getDescrizione() : "");
            bean.setRequestDate(entity.getRequestTime());
            bean.setAnswered(entity.isAnswered());
            bean.setResponse(entity.getResponse());
            bean.setDietTaken(converterTaken(entity.getDietTaken()));
            return bean;
        }

    public DietTakenBean converterTaken(TakenDietEntity entity){

        DietTakenBean dieta = new DietTakenBean();

        entity.getDays().forEach(dayEntity -> {
            DayTakenBean day = new DayTakenBean();
            dayEntity.getPasti().forEach(mealEntity -> {
                MealTakenBean meal = new MealTakenBean();
                meal.setNome(mealEntity.getNome());
                meal.setDescrizione(mealEntity.getDescrizione());
                meal.setKcal(mealEntity.getKcal());
                day.addMeal(meal);
            });
            dieta.addDay(day);
        });
        return dieta;

    }

    public ReportReponseBean getLatestResponseForUser() {


        String userId = SessionManagerSLT.getInstance().getLoggedInUsername();

        return ReportRequestDAO.getInstance()
                .getRamStorage()
                .stream()
                .filter(r -> r.getDietTaken().getUser().equalsIgnoreCase(userId))
                .filter(r -> r.getResponse() != null && !r.getResponse().isBlank())
                .max(Comparator.comparing(ReportRequestEntity::getRequestTime))
                .map(req -> new ReportReponseBean(
                        req.getDietAssigned() != null ? req.getDietAssigned().getNome() : "Dieta sconosciuta",
                        req.getNutritionistEmail(),
                        req.getResponse()
                ))
                .orElse(null);
    }



    public void deletediet() {

        String userId = SessionManagerSLT.getInstance().getLoggedInUsername();

        ReportRequestDAO.getInstance().deleteByUser(userId);
        UserDietDAO.getInstance().deleteByUserEmail(SessionManagerSLT.getInstance().getLoggedmail());
        DietTakenDAO.getInstance().deleteByUser(SessionManagerSLT.getInstance().getLoggedInUsername());

        notifyObservers();


    }

    @Override
    public void registerObserver(ReportObserver o) {
        this.observer = o;
    }

    @Override
    public void notifyObservers() {
        if (observer != null) {
            observer.update();
        }
    }
}


