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

        List<ReportRequestEntity> allRequests = ReportRequestDAO.getInstance().getRamStorage();

        return allRequests.stream()
                .filter(req -> {
                    boolean match = req.getNutritionistEmail().equalsIgnoreCase(baseUser.getNome()) && !req.isAnswered();
                    return match;
                })
                .toList();
    }
}
