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

    private String viewMenu="menu-view.fxml";

    @FXML
    private VBox resocontoBox;

    @FXML
    private Button home;

    @FXML
    private Button end;

    @FXML
    private Button asknutrbutton;

    private FollowDietController controller;

    @FXML
    public void initialize() {

        seeResults();

    }

    @FXML
    public void gohome() {

        Stage stage = (Stage)home .getScene().getWindow();
        GraphicController.cambiascena(stage,viewMenu );

    }

    @FXML
    public void endDiet(){

        controller = new FollowDietController();
        controller.delete(SessionManagerSLT.getInstance().getLoggedInUsername(),SessionManagerSLT.getInstance().getLoggedmail());

        Stage stage = (Stage)end.getScene().getWindow();
        GraphicController.cambiascena(stage, viewMenu);


    }

    @FXML
    public void asknutr(){

        SessionManagerSLT.getInstance().setRequestnutr(true);
        controller = new FollowDietController();
        if(Boolean.TRUE.equals(controller.requNutr())){
            Stage stage = (Stage)asknutrbutton.getScene().getWindow();
            GraphicController.cambiascena(stage, viewMenu);
        }

    }

    private void seeResults(){
        controller = new FollowDietController();
        List<String> resoconto = controller.generaResoconto();

        for (String giorno : resoconto) {
            Label label = new Label(giorno);
            label.setStyle("-fx-font-style: italic; -fx-font-size: 16px;");
            label.setWrapText(true);
            resocontoBox.getChildren().add(label);
        }
    }


}
