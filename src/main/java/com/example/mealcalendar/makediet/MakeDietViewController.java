package com.example.mealcalendar.makediet;

import com.example.mealcalendar.GraphicController;
import com.example.mealcalendar.MailSendingException;
import com.example.mealcalendar.SessionManagerSLT;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

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

        LocalDate today = LocalDate.now();
        DietaBean dietaBean = new DietaBean();
        dietaBean.setNome(nomedieta.getText());
        dietaBean.setDescrizione(descrizione.getText());

        List<GiornoBean> giorni = new ArrayList<>();

        List<VBox> colonne = List.of(colonnaSinistra, colonnaDestra);

        int numerogiorno=0;
        for (VBox colonna : colonne) {
            for (Node node : colonna.getChildren()) {
                numerogiorno++;
                if (node instanceof VBox giornoBox) {
                    GiornoBean giorno = new GiornoBean();
                    giorno.setNgiorno(numerogiorno);
                    List<TextField> fields = giornoBox.getChildren().stream()
                            .filter(n -> n instanceof TextField)
                            .map(n -> (TextField) n)
                            .toList();

                    PastoBean colazione = new PastoBean();
                    colazione.setNomePiatto(fields.get(0).getText());
                    colazione.setCalorie(Integer.parseInt(fields.get(1).getText()));

                    PastoBean pranzo = new PastoBean();
                    pranzo.setNomePiatto(fields.get(2).getText());
                    pranzo.setCalorie(Integer.parseInt(fields.get(3).getText()));

                    PastoBean cena = new PastoBean();
                    cena.setNomePiatto(fields.get(4).getText());
                    cena.setCalorie(Integer.parseInt(fields.get(5).getText()));

                    giorno.setColazione(colazione);
                    giorno.setPranzo(pranzo);
                    giorno.setCena(cena);

                    giorni.add(giorno);
                }
            }
        }
        dietaBean.setGiorni(giorni);
        dietaBean.setDurata(numerogiorno);
        dietaBean.setAutore(SessionManagerSLT.getInstance().getLoggedInUsername());
        dietaBean.setDataPubblicazione(today.toString());

        MakeDietaController controller= new MakeDietaController();
        if(controller.saveDieta(dietaBean)){
            Stage stage = (Stage) backbutton.getScene().getWindow();
            GraphicController.cambiascena(stage, "makediet-view.fxml");
        }

    }

}
