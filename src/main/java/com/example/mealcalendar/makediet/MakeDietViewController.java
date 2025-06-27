package com.example.mealcalendar.makediet;

import com.example.mealcalendar.GraphicController;
import com.example.mealcalendar.MailSendingException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MakeDietViewController {

    @FXML
    private Button backbutton;

    @FXML
    private Button conferma;

    @FXML
    private Button annulla;

    @FXML
    private Button generascheda;

    @FXML
    private SplitMenuButton duratadieta;

    @FXML
    private MenuItem durata;

    @FXML
    private VBox colonnaDestra;

    @FXML
    private VBox colonnaSinistra;

    @FXML
    private TextField nomedieta;

    @FXML
    private TextArea descrizione;

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private void goBack(ActionEvent event) {
        Stage stage = (Stage) backbutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "usermenu-view.fxml");

    }

    @FXML
    public void initialize() {
        durata.setOnAction(e -> duratadieta.setText("7"));

        generascheda.setOnAction(event -> {
            int giorni=Integer.parseInt(duratadieta.getText());
            generaSezionePasti(giorni);
        });


    }

    private void generaSezionePasti(int giorni) {
        colonnaSinistra.getChildren().clear();
        colonnaDestra.getChildren().clear();

        for (int i = 1; i <= giorni; i++) {
            VBox giornoBox = new VBox(5);
            giornoBox.setStyle("-fx-border-color: #ccc; -fx-padding: 10;");

            Label giornoLabel = new Label("Giorno " + i);
            TextField colazione = new TextField(); colazione.setPromptText("Colazione");
            TextField nKcalc = new TextField();
            nKcalc.setPromptText("n.Kcal colazione");
            TextField pranzo = new TextField(); pranzo.setPromptText("Pranzo");
            TextField nKcalp = new TextField();
            nKcalp.setPromptText("n.Kcal pranzo");
            TextField cena = new TextField(); cena.setPromptText("Cena");
            TextField nKcalce = new TextField();
            nKcalce.setPromptText("n.Kcal cena");

            giornoBox.getChildren().addAll(giornoLabel, colazione, nKcalc, pranzo, nKcalp, cena, nKcalce);

            if (i <= Math.ceil(giorni / 2.0)) {
                colonnaSinistra.getChildren().add(giornoBox);
            } else {
                colonnaDestra.getChildren().add(giornoBox);
            }
        }
    }

    @FXML
    private void clean(ActionEvent event) {
        Stage stage = (Stage) annulla.getScene().getWindow();
        GraphicController.cambiascena(stage, "makediet-view.fxml");
    }

    @FXML
    private void confirm(ActionEvent event) {
        //salverà qualcosa

    }

}
