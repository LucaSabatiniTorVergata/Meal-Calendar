package com.example.mealcalendar.findrest;

import com.example.mealcalendar.*;
import com.example.mealcalendar.seteatingtime.MealCalenderViewController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.function.UnaryOperator;

import static com.example.mealcalendar.seteatingtime.MealCalenderViewController.*;


public class FindRestaurantViewController {

    private static final boolean DEBUG = true;

    private static final Logger LOGGER = Logger.getLogger(FindRestaurantViewController.class.getName());

    @FXML
    private Label lableemail;

    @FXML
    private ProgressIndicator loadingIndicator;

    @FXML
    private SplitMenuButton tipoDieta;
    @FXML
    private TextField city;

    @FXML
    private MenuItem vegan;
    @FXML
    private MenuItem vegetariana;
    @FXML
    private MenuItem omnivora;


    @FXML
    private Label welcomelabel;

    @FXML
    private ListView<RistoranteBean> ristorantiListView;

    @FXML
    private Button homebutton;

    @FXML
    private Button registerbutton;

    @FXML
    private Button findrecipebutton;
    @FXML
    private Button seteatingtimebutton;
    @FXML
    private Button fillfridgebutton;

    private String tipoDietaSelezionato;
    private double cittaSelezionata;

    @FXML
    private void confermafiltri(ActionEvent event) {
        try {
            tipoDietaSelezionato = tipoDieta.getText();
            String nomeCitta = city.getText().trim();


            LOGGER.log(Level.INFO, "Filtri confermati:");
            LOGGER.log(Level.INFO, "Dieta: {0}", tipoDietaSelezionato);
            LOGGER.log(Level.INFO, "Città: {0}", nomeCitta);

            RistoranteBean filtro = new RistoranteBean();
            filtro.setCitta(nomeCitta);  // Metodo corretto

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore durante la conferma dei filtri", e);
        }
    }


    @FXML
    public void initialize() {
        String username = SessionManagerSLT.getInstance().getLoggedInUsername();
        if (username != null) {
            welcomelabel.setText("Hi, " + username + "!");
        }

        vegan.setOnAction(e -> tipoDieta.setText("Vegano"));
        vegetariana.setOnAction(e -> tipoDieta.setText("Vegetariano"));
        omnivora.setOnAction(e -> tipoDieta.setText("Onnivoro"));


        Pattern validEditingState = Pattern.compile("\\d*");
        UnaryOperator<TextFormatter.Change> filter = change -> validEditingState.matcher(change.getControlNewText()).matches() ? change : null;
        TextFormatter<String> textformatter = new TextFormatter<>(filter);
        city.setTextFormatter(textformatter);
    }

    public void mostraRistoranti(List<RistoranteBean> listaRistoranti) {
        ristorantiListView.getItems().clear();
       // this.listaRistoranti = listaRistoranti;
        ristorantiListView.getItems().addAll(listaRistoranti);
    }

    @FXML
    private void registerGuest(ActionEvent event)  {
        Stage stage = (Stage) registerbutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "register-view.fxml");
    }

    @FXML
    private void loadHomePageGuest(ActionEvent event) {
        Stage stage = (Stage) homebutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "guestmenu-view.fxml");
    }

    @FXML
    private void loadHomePageUser(ActionEvent event) {
        Stage stage = (Stage) homebutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "usermenu-view.fxml");
    }

    @FXML
    private void recipeuser(ActionEvent event) {
        Stage stage = (Stage) findrecipebutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "recipe-view.fxml");
    }

    @FXML
    private void fridgeuser(ActionEvent event) {
        Stage stage = (Stage) fillfridgebutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "insertmeal-view.fxml");
    }

    @FXML
    private void calendaruser(ActionEvent event) {
        Stage stage = (Stage) seteatingtimebutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "mealcalendar-view.fxml");
    }
}
