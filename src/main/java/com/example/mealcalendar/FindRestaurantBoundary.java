package com.example.mealcalendar;

import javafx.scene.input.MouseEvent;
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
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javafx.scene.control.ListView;


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

    @FXML
    private ListView<ReturnRestaurantsBean> RistorantiListView;// ListView per mostrare i ristoranti

    private String TipoDietaSelezionato;
    private String PastoSelezionato;
    private double DistanzaInserita;

    private List<ReturnRestaurantsBean> ListaRistoranti;

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
        if(TipoDieta.getText().equalsIgnoreCase("Onnivoro")){
            TipoDietaSelezionato = "";

        }
        else {
            TipoDietaSelezionato = TipoDieta.getText();
        }
        PastoSelezionato = Pasto.getText();
        DistanzaInserita= Double.parseDouble(Distanza.getText());
        System.out.println("Filtri confermati:");
        System.out.println("Dieta: " + TipoDietaSelezionato);
        System.out.println("Pasto: " + PastoSelezionato);
        System.out.println("Distanza: " + DistanzaInserita + " km");
        FiltersRestaurantBean Filtro = new FiltersRestaurantBean(TipoDietaSelezionato, PastoSelezionato, DistanzaInserita);
        ListaRistoranti = Controller.TrovaRistorante(Filtro);
        MostraRistoranti(ListaRistoranti);

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
    //Metodo per mostrare i ristoranti nella ListView
    public void MostraRistoranti(List<ReturnRestaurantsBean> ListaRistoranti) {
        this.ListaRistoranti = ListaRistoranti;
        RistorantiListView.getItems().clear();
        RistorantiListView.getItems().addAll(ListaRistoranti);
    }
    //Metodo per aprire Google Maps nel browser con le coordinate
        private void ApriGoogleMaps(double lat, double lng) {
            String url = "https://www.google.com/maps/search/?api=1&query=" + lat + "," + lng;
            try {
                Desktop.getDesktop().browse(new URI(url)); // Apriamo il browser
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }


    //Metodo chiamato quando l'utente clicca su un ristorante
    @FXML
    private void handleclick(MouseEvent event) {
        if (event.getClickCount() == 2) { // Doppio click per aprire Google Maps
            int selectedIndex = RistorantiListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                ReturnRestaurantsBean Ristorante = ListaRistoranti.get(selectedIndex);
                ApriGoogleMaps(Ristorante.getLatitudine(), Ristorante.getLongitudine());
            }
        }
    }



}

