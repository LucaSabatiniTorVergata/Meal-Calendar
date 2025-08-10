package com.example.mealcalendar.view_controller;

import com.example.mealcalendar.GraphicController;
import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.controller_applicativo.FollowDietController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.util.List;

public class ResultsoDietViewController {

    @FXML
    private VBox resocontoBox;

    @FXML
    private Button home;

    @FXML
    private Button end;

    @FXML
    private Button asknutrbutton;

    @FXML
    public void initialize() {

        FollowDietController controller = new FollowDietController();
        List<String> resoconto = controller.generaResoconto();

        for (String giorno : resoconto) {
            Label label = new Label(giorno);
            label.setStyle("-fx-font-style: italic; -fx-font-size: 16px;");
            label.setWrapText(true);
            resocontoBox.getChildren().add(label);
        }
    }

    @FXML
    public void gohome() {

        Stage stage = (Stage)home .getScene().getWindow();
        GraphicController.cambiascena(stage, "menu-view.fxml");

    }

    @FXML
    public void endDiet(){

        FollowDietController controller = new FollowDietController();
        controller.delete(SessionManagerSLT.getInstance().getLoggedInUsername(),SessionManagerSLT.getInstance().getLoggedmail());

        Stage stage = (Stage)end.getScene().getWindow();
        GraphicController.cambiascena(stage, "menu-view.fxml");


    }

    @FXML
    public void asknutr(){

        SessionManagerSLT.getInstance().setRequestnutr(true);
        FollowDietController controller = new FollowDietController();
        if(controller.requnutr()){
            Stage stage = (Stage)asknutrbutton.getScene().getWindow();
            GraphicController.cambiascena(stage, "menu-view.fxml");
        }

    }


}
