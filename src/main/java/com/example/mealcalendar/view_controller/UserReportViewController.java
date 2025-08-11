package com.example.mealcalendar.view_controller;

import com.example.mealcalendar.GraphicController;
import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.bean.ReportReponseBean;
import com.example.mealcalendar.controller_applicativo.RequestNutritionsReportController;
import com.example.mealcalendar.model.ReportRequestEntity;
import com.example.mealcalendar.patternobserver.ReportObserver;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.Observer;

public class UserReportViewController implements ReportObserver {


    @FXML
    private Label lblDietName;

    @FXML
    private Label lblNutritionist;

    @FXML
    private TextArea txtResponse;

    @FXML
    private Button btnComplete;

    private ReportReponseBean currentReport;

    private RequestNutritionsReportController controller;

    public void initialize() {
        controller = new RequestNutritionsReportController();
        controller.registerObserver(this);
        loadReport();
    }

    private void loadReport() {
        currentReport = controller.getLatestResponseForUser();

        if (currentReport != null) {
            lblDietName.setText(currentReport.getDietName());
            lblNutritionist.setText(currentReport.getNutritionistName());
            txtResponse.setText(currentReport.getResponseText());
            btnComplete.setDisable(false);
        } else {
            lblDietName.setText("Nessun resoconto disponibile");
            lblNutritionist.setText("");
            txtResponse.setText("");
            btnComplete.setDisable(true);
        }
    }

    @Override
    public void update() {
        loadReport();
    }

    @FXML
    private void close() {
        if (currentReport != null) {
            controller.deletediet();
            loadReport();// Aggiorna la view subito
            Stage stage = (Stage)btnComplete.getScene().getWindow();
            GraphicController.cambiascena(stage, "menu-view.fxml");
        }
    }
}

