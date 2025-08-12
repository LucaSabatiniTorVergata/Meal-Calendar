package  com.example.mealcalendar;

import com.example.mealcalendar.dao.ReportRequestDAO;
import com.example.mealcalendar.model.ReportRequestEntity;
import com.example.mealcalendar.model.UserEntity;
import com.example.mealcalendar.patternobserver.ReportRequestNotifier;

import java.util.List;
import java.util.Objects;


public class UserWithNutritionistRole extends UserEntity {

    private final UserEntity baseUser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserWithNutritionistRole that)) return false;
        return Objects.equals(baseUser, that.baseUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseUser);
    }

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
                .filter(req -> (req.getNutritionistEmail().equalsIgnoreCase(baseUser.getNome()) && !req.isAnswered()))
                .toList();
    }


    public void respondToReportRequest(ReportRequestEntity request, String responseText) {

        request.setResponse(responseText);
        request.setAnswered(true);
        ReportRequestDAO.getInstance().update(request);
        ReportRequestNotifier.getInstance().notifyObservers();
    }
}
