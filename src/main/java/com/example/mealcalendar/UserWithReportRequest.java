package com.example.mealcalendar;

import com.example.mealcalendar.dao.ReportRequestDAO;
import com.example.mealcalendar.model.DietEntity;
import com.example.mealcalendar.model.ReportRequestEntity;
import com.example.mealcalendar.model.TakenDietEntity;
import com.example.mealcalendar.model.UserEntity;

import java.time.LocalDateTime;


public class UserWithReportRequest extends UserEntity {

    private final UserEntity baseUser;

    public UserWithReportRequest(UserEntity baseUser) {

        super(baseUser.getNome(),baseUser.getEmail(), baseUser.getRuolo());
        this.baseUser = baseUser;

    }

    // Delego i metodi principali all’utente base
    @Override
    public String getEmail() {
        return baseUser.getEmail();
    }
    @Override
    public String getRuolo() {
        return baseUser.getRuolo();
    }


    // Metodo specifico per inviare richiesta resoconto
    public void sendReportRequest(TakenDietEntity takenDiet, DietEntity assignedDiet, String nutritionist) {
        ReportRequestEntity request = new ReportRequestEntity(
                baseUser.getNome(),
                nutritionist,
                assignedDiet,
                takenDiet,
                LocalDateTime.now()
        );

        ReportRequestDAO.getInstance().save(request);
        System.out.println("[UserWithReportRequest] Richiesta inviata al nutrizionista: " + nutritionist);
    }
}


