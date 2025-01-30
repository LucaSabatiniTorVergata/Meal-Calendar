package com.example.mealcalendar;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class FindRestaurantBoundary {

    @FXML
    private SplitMenuButton TipoDieta;
    @FXML
    private SplitMenuButton Pasto;
    @FXML
    private TextField Distanza;

    @FXML
    private MenuItem Vegan;
    @FXML
    private MenuItem Vegetariana;
    @FXML
    private MenuItem Omnivora;

    @FXML
    private MenuItem Colazione;
    @FXML
    private MenuItem Pranzo;
    @FXML
    private MenuItem Cena;

    private String TipoDietaSelezionato;
    private String PastoSelezionato;
    private double DistanzaInserita;

    private ChooseRestaurantController Controller = new ChooseRestaurantController();

    @FXML
    private void LoadHomePageGuest(ActionEvent event) throws IOException {
        Parent NuovaSchermata = FXMLLoader.load(getClass().getResource("guestmenu-view.fxml"));
        Scene NuovaScena = new Scene(NuovaSchermata);
        Stage Finestra = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Finestra.setScene(NuovaScena);
        Finestra.show();

    }

    @FXML
    private void RegisterGuest(ActionEvent event) throws IOException {
        Parent NuovaSchermata = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene NuovaScena = new Scene(NuovaSchermata);
        Stage Finestra = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Finestra.setScene(NuovaScena);
        Finestra.show();

    }

    @FXML
    private void LoadHomePageUser(ActionEvent event) throws IOException {
        Parent NuovaSchermata = FXMLLoader.load(getClass().getResource("usermenu-view.fxml"));
        Scene NuovaScena = new Scene(NuovaSchermata);
        Stage Finestra = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Finestra.setScene(NuovaScena);
        Finestra.show();
    }

    @FXML
    private void confermafiltri(ActionEvent event) throws IOException {
        // Solo dopo aver premuto "conferma" copiamo i dati
        TipoDietaSelezionato = TipoDieta.getText();
        PastoSelezionato = Pasto.getText();
        DistanzaInserita= Double.parseDouble(Distanza.getText());
        System.out.println("Filtri confermati:");
        System.out.println("Dieta: " + TipoDietaSelezionato);
        System.out.println("Pasto: " + PastoSelezionato);
        System.out.println("Distanza: " + DistanzaInserita + " km");
        FiltersRestaurantBean Filtro = new FiltersRestaurantBean(TipoDietaSelezionato, PastoSelezionato, DistanzaInserita);
        Controller.TrovaRistorante(Filtro);
    }

    @FXML
    public void initialize() throws IOException {
        // Associare le azioni ai MenuItem (cambiano il testo del bottone)
        Vegan.setOnAction(e -> TipoDieta.setText("Vegano"));
        Vegetariana.setOnAction(e -> TipoDieta.setText("Vegetariano"));
        Omnivora.setOnAction(e -> TipoDieta.setText("Onnivoro"));

        Colazione.setOnAction(e -> Pasto.setText("Colazione"));
        Pranzo.setOnAction(e -> Pasto.setText("Pranzo"));
        Cena.setOnAction(e -> Pasto.setText("Cena"));
        try {
            DistanzaInserita = Double.parseDouble(Distanza.getText());
        } catch (NumberFormatException e) {
            System.out.println("Errore: Inserisci un numero valido per la distanza!");
            return;
        }



    }





}

