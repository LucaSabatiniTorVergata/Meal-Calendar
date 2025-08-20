package com.example.mealcalendar.view_controller;

import com.example.mealcalendar.GraphicController;
import com.example.mealcalendar.bean.ReportRequestBean;
import com.example.mealcalendar.controller_applicativo.RequestNutritionsReportController;
import com.example.mealcalendar.patternobserver.observerasknutri.ReportRequestNotifier;
import com.example.mealcalendar.patternobserver.observerasknutri.ReportRequestObserver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class ViewAnsByNutritionistViewController implements ReportRequestObserver {



    @FXML
    private ListView<ReportRequestBean> requestsListView;

    @FXML
    private TextArea requestDetails;

    @FXML
    private TextArea responseArea;

    @FXML
    private Button sendResponseButton;

    private final RequestNutritionsReportController controller = new RequestNutritionsReportController();
    private final ObservableList<ReportRequestBean> requests = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        ReportRequestNotifier.getInstance().registerObserver(this);

        // Recupera le richieste pendenti
        if(requests.isEmpty())
        {
            List<ReportRequestBean> pending = controller.getPendingRequestsForNutritionist();
            requests.setAll(pending);

        }

        requestsListView.setItems(requests);

        // Definisce come visualizzare gli elementi nella ListView
        requestsListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(ReportRequestBean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(item.getDietName() + " - Utente: " + item.getUserEmail());
                    setTooltip(new Tooltip(item.getDietDescription()));
                }
            }
        });

        // Listener per la selezione
        requestsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) {
                showRequestDetails(newV);
            }
        });
    }

    private void showRequestDetails(ReportRequestBean request) {

        StringBuilder details = new StringBuilder();
        details.append("Utente: ").append(request.getUserEmail()).append("\n")
                .append("Dieta: ").append(request.getDietName()).append("\n")
                .append("Descrizione: ").append(request.getDietDescription()).append("\n")
                .append("Data richiesta: ").append(request.getRequestDate()).append("\n\n");

        // --- Mostra la DietTaken ---
        if (request.getDietTaken() != null && !request.getDietTaken().getDietTaken().isEmpty()) {
            for (int i = 0; i < request.getDietTaken().getDietTaken().size(); i++) {
                details.append("Giorno ").append(i + 1).append(":\n");
                request.getDietTaken().getDietTaken().get(i).getMealsTaken().forEach(meal -> details.append("  • ").append(meal.getNome())
                        .append(" (").append(meal.getKcal()).append(" kcal)\n")
                        .append("    ").append(meal.getDescrizione()).append("\n"));
                details.append("\n");
            }
        } else {
            details.append("Nessun pasto registrato.\n");
        }

        requestDetails.setText(details.toString());

        // Risposta precompilata se esiste
        responseArea.setText(request.getResponse() != null ? request.getResponse() : "");
    }

    @FXML
    private void onSendResponse() {

        ReportRequestBean selected = requestsListView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }

        selected.setResponse(responseArea.getText());
        selected.setAnswered(true);

        // Aggiorna in persistenza
        controller.updateResponse(selected);

        // Torna al menu
        Stage stage = (Stage) sendResponseButton.getScene().getWindow();
        GraphicController.cambiascena(stage, "menu-view.fxml");
    }

    @Override
    public void onNewReportRequest() {
        // Aggiorna la lista quando arriva una nuova richiesta
        List<ReportRequestBean> pending = controller.getPendingRequestsForNutritionist();
        requests.setAll(pending);

    }

}
