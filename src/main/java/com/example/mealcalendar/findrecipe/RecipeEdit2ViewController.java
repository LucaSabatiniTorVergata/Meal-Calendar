package com.example.mealcalendar.findrecipe;

import java.io.IOException;
import java.util.logging.Logger;

import com.example.mealcalendar.GraphicController;
import com.example.mealcalendar.SessionManagerSLT;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class RecipeEdit2ViewController {

    private static final Logger LOGGER = Logger.getLogger(RecipeEdit2ViewController.class.getName());

    @FXML
    private Label welcomelabel;

    @FXML
    private Button homereturn;
    @FXML
    private Button returntoedit;
    @FXML
    private Button apply;
    @FXML
    private TextField nomeRicetta;
    @FXML
    private SplitMenuButton tipodieta;
    @FXML
    private SplitMenuButton tipopasto;
    @FXML
    private TextField descrizione;
    @FXML
    private TextField numeroing;
    @FXML
    private TextField ingredienti;

    @FXML
    private MenuItem vegan;
    @FXML
    private MenuItem vegetariana;
    @FXML
    private MenuItem omnivora;

    @FXML
    private MenuItem colazione;
    @FXML
    private MenuItem pranzo;
    @FXML
    private MenuItem cena;

    private String ricetta;
    private String numIngredienti;
    private String ingredientiRicetta;
    private String descrizioneRicetta;
    private String dieta;
    private String pasto;

    private String ricettascelta;

    @FXML
    private void homeview(ActionEvent event) {
        Stage stage = (Stage) homereturn.getScene().getWindow();
        GraphicController.cambiascena(stage, "usermenu-view.fxml");
    }

    @FXML
    private void recipeeditview(ActionEvent event) {
        Stage stage = (Stage) returntoedit.getScene().getWindow();
        GraphicController.cambiascena(stage, "recipeedit-view.fxml");
    }

    public void setRecipe(String recipe) {
        String username = SessionManagerSLT.getInstance().getLoggedInUsername();
        if (username != null) {
            welcomelabel.setText("Hi, " + username + "!");
        }

        this.ricettascelta = recipe;
        updateUI();
    }

    public void updateUI() {
        if (ricettascelta != null) {
            String[] parts = ricettascelta.split(" - ");
            if (parts.length >= 6) {
                nomeRicetta.setText(parts[0]);
                tipodieta.setText(parts[1]);
                tipopasto.setText(parts[2]);
                numeroing.setText(parts[3]);
                ingredienti.setText(parts[4]);
                descrizione.setText(parts[5]);
            }
        } else {
            LOGGER.info("Errore: ricettascelta è null!");
        }

        vegan.setOnAction(e -> tipodieta.setText("Vegan"));
        vegetariana.setOnAction(e -> tipodieta.setText("Vegetarian"));
        omnivora.setOnAction(e -> tipodieta.setText("Omnivorous"));

        colazione.setOnAction(e -> tipopasto.setText("Breakfast"));
        pranzo.setOnAction(e -> tipopasto.setText("Lunch"));
        cena.setOnAction(e -> tipopasto.setText("Dinnner"));
    }

    @FXML
    private void applyview(ActionEvent event) throws IOException, RecipeDaoException {

        ricetta = nomeRicetta.getText();
        numIngredienti = numeroing.getText();
        ingredientiRicetta = ingredienti.getText();
        descrizioneRicetta = descrizione.getText();
        pasto = tipopasto.getText();
        dieta = tipodieta.getText();

        // Utilizzo del Builder Pattern per creare un nuovo oggetto RecipeEdit2Bean
        RecipeEdit2Bean bean = new RecipeEdit2Bean.Builder()
                .setRicettapresa(ricettascelta)
                .setRicetta(ricetta)
                .setTdieta(dieta)
                .setTpasto(pasto)
                .setNumingred(numIngredienti)
                .setIngred(ingredientiRicetta)
                .setDesrcip(descrizioneRicetta)
                .setAutor(SessionManagerSLT.getInstance().getLoggedInUsername())
                .build();


        // Passiamo il bean al controller
        RecipeEdit2Controller controller = new RecipeEdit2Controller(bean);
        controller.cambiaRicetta();

        // Cambiamo la scena
        Stage stage = (Stage) apply.getScene().getWindow();
        GraphicController.cambiascena(stage, "recipe-view.fxml");
    }
}
