package com.example.mealcalendar;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import java.util.List;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;


import static com.example.mealcalendar.MealCalenderViewBoundary.vengoDaCalendar;


public class RecipeViewBoundary {

    @FXML
    private Button addrecipe;
    @FXML
    private Button editrecipe;
    @FXML
    private Button returnhome;


    @FXML
    private SplitMenuButton tipoDieta;
    @FXML
    private SplitMenuButton tipoPasto;


    @FXML
    private MenuItem omnivorous ;
    @FXML
    private MenuItem vegetarian;
    @FXML
    private MenuItem vegan;

    @FXML
    private MenuItem breakfast;
    @FXML
    private MenuItem launch;
    @FXML
    private MenuItem dinner;

    @FXML
    private ListView<String> listaRicetteview;

    @FXML
    private Pane detailpane;

    @FXML
    private Label label;

    @FXML
    private Label detailLabel;

    private List<RecipeReturnBean> listaricette;

    private String tipoDietaSelezionato;
    private String pastoSelezionato;



    @FXML
    private void addrecipeview(ActionEvent event) {

        Stage stage = (Stage) addrecipe.getScene().getWindow();
        GraphicController.cambiascena(stage, "recipeadd-view.fxml");
    }

    @FXML
    private void editrecipeview(ActionEvent event) throws IOException {

        Stage stage = (Stage) editrecipe.getScene().getWindow();
        GraphicController.cambiascena(stage, "recipeedit-view.fxml");
    }
    @FXML
    private void homeView(ActionEvent event) throws IOException {

        Stage stage = (Stage) returnhome.getScene().getWindow();
        GraphicController.cambiascena(stage, "usermenu-view.fxml");
    }


    @FXML
    private void searchrecipies(ActionEvent event) throws IOException {

        tipoDietaSelezionato = tipoDieta.getText();
        pastoSelezionato= tipoPasto.getText();
        RecipeSearchFiltersBean bean=new RecipeSearchFiltersBean(tipoDietaSelezionato,pastoSelezionato);
        RecipeSearchController controller=new RecipeSearchController(bean);
        List<RecipeReturnBean> ricettereturnbean=controller.trovaricette();

        if(ricettereturnbean!=null){
            label.setVisible(false);
            mostraricette(ricettereturnbean);
        }else{
            listaRicetteview.getItems().clear();
            label.setVisible(true);
        }
    }



    @FXML
    public void initialize() throws IOException {


        // Associare le azioni ai MenuItem (cambiano il testo del bottone)
        vegan.setOnAction(e -> tipoDieta.setText("Vegan"));
        vegetarian.setOnAction(e -> tipoDieta.setText("Vegetarian"));
        omnivorous.setOnAction(e -> tipoDieta.setText("Omnivorous"));

        breakfast.setOnAction(e -> tipoPasto.setText("Breakfast"));
        launch.setOnAction(e -> tipoPasto.setText("Lunch"));
        dinner.setOnAction(e -> tipoPasto.setText("Dinner"));
    }

    public void mostraricette(List<RecipeReturnBean> listaRicette) {
        listaRicetteview.getItems().clear();
        for (RecipeReturnBean ricetta : listaRicette) {
            String nomeRicetta = ricetta.getRecipeName();  // Prendi il nome
            System.out.println("nomeRicetta: " + nomeRicetta);

            String tipodieta = ricetta.getTypeofDiet();    // Prendi il tipo di dieta
            System.out.println("tipodieta: " + tipodieta);

            String tipopasto = ricetta.getTypeofMeal(); // Prendi il tipo di pasto
            System.out.println("tipopasto: " + tipopasto);

            String numingredienti = ricetta.getNumIngredients();
            System.out.println("numingredienti: " + numingredienti);

            String ingredienti = ricetta.getIngredients();
            System.out.println("ingredienti: " + ingredienti);

            String descrizione = ricetta.getDescription();
            System.out.println("descrizione: " + descrizione);

            String author = ricetta.getAuthor();
            System.out.println("author: " + author);

            String riga = nomeRicetta + " - " + tipodieta + " - " + tipopasto
                    + " - " + numingredienti + " - " + ingredienti + " - " + descrizione + " - " + author;  // Stringa da mostrare
            System.out.println("riga: " + riga);

            listaRicetteview.getItems().add(riga);
        }
    }


    @FXML
    private void handleItemClick(MouseEvent event) {
        if (event.getClickCount() == 2) {  // Controlla se è un doppio click
            String selectedItem = listaRicetteview.getSelectionModel().getSelectedItem();

            if(vengoDaCalendar==true){
                Stage stage = (Stage) returnhome.getScene().getWindow();
                vengoDaCalendar=false;
                GraphicController.cambiascena(stage,"mealcalendar-view.fxml");
                return;
            }

            if (selectedItem != null) {
                detailpane.setVisible(true);
                listaRicetteview.setVisible(false);
                detailLabel.setText(selectedItem);

            }
        }
    }

    @FXML
    private void handleback() {
        detailpane.setVisible(false);
        listaRicetteview.setVisible(true);
    }
}
