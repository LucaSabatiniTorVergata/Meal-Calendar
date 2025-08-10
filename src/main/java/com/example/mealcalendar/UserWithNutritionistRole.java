package  com.example.mealcalendar;

import com.example.mealcalendar.dao.ReportRequestDAO;
import com.example.mealcalendar.model.ReportRequestEntity;
import com.example.mealcalendar.model.UserEntity;

import java.util.List;



public class UserWithNutritionistRole extends UserEntity {

    private final UserEntity baseUser;

    public UserWithNutritionistRole(UserEntity baseUser) {
        super(baseUser.getNome(), baseUser.getEmail(), baseUser.getRuolo());
        this.baseUser = baseUser;
    }



    @Override
    public String getEmail() {
        return baseUser.getEmail();
    }
    @Override
    public String getRuolo() {
        return baseUser.getRuolo();
    }


    public List<ReportRequestEntity> getPendingReportRequests() {


        System.out.println("[DEBUG] baseUser email: " + baseUser.getNome());
        List<ReportRequestEntity> allRequests = ReportRequestDAO.getInstance().getRamStorage();

        allRequests.forEach(r -> System.out.println(" - Richiesta Nutrizionista: '" + r.getNutritionistEmail() + "', risposta: " + r.isAnswered()));

        return allRequests.stream()
                .filter(req -> {
                    boolean match = req.getNutritionistEmail().equalsIgnoreCase(baseUser.getNome()) && !req.isAnswered();
                    System.out.println("[DEBUG] filtro richiesta nutriEmail='" + req.getNutritionistEmail() + "' baseUserEmail='" + baseUser.getNome() + "' match=" + match);
                    return match;
                })
                .toList();
    }


    public void respondToReportRequest(ReportRequestEntity request, String responseText) {
        request.setResponse(responseText);
        request.setAnswered(true);
        ReportRequestDAO.getInstance().update(request);
        System.out.println("[UserWithNutritionistRole] Risposta inviata per richiesta ID: " + request.getUserEmail());
    }
}
