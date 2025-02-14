package com.example.mealcalendar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.util.Callback;
import java.time.LocalDate;

public class MealCalenderViewBoundary {

    private static final Logger LOGGER = Logger.getLogger(MealCalenderViewBoundary.class.getName());

    @FXML
    private Button backhome;

    @FXML
    private SplitMenuButton posizione;

    @FXML
    private DatePicker calendar;

    @FXML
    private Button confirmButton;

    @FXML
    private MenuItem home;

    @FXML
    private MenuItem restaurant;

    @FXML
    private TextField orascelta;


    public static boolean sceltaLuogo = false;
    public static boolean vengoDaCalendar = false;
    public static String ristorantescelto;
    public static String ricettascelta;

    private static LocalDate dataselezionata;
    private static String oraselezionata;


    @FXML
    private void initialize() {

        calendar.setDayCellFactory(disablePastDates());

        home.setOnAction(e -> {
            posizione.setText("home");
            sceltaLuogo = false;
        });

        restaurant.setOnAction(e -> {
            posizione.setText("restaurant");
            sceltaLuogo = true;
        });

        Pattern pattern = Pattern.compile("([01]?[0-9]|2[0-3])?:?[0-5]?[0-9]?");

        UnaryOperator<TextFormatter.Change> filter = change -> {
            if (pattern.matcher(change.getControlNewText()).matches()) {
                return change;
            }
            return null;
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        orascelta.setTextFormatter(textFormatter);


        LOGGER.log(Level.INFO, "Scelta luogo: {0}", sceltaLuogo);
    }

    @FXML
    private void confirmChoise(ActionEvent actionEvent) throws Exception {


        dataselezionata = calendar.getValue();
        oraselezionata=orascelta.getText();

        if(sceltaLuogo){
            vengoDaCalendar=true;
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            GraphicController.cambiascena(stage, "findrestaurantuser-view.fxml");


        }else{
            vengoDaCalendar=true;
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            GraphicController.cambiascena(stage, "recipe-view.fxml");

        }

    }

    public static void inviomail() throws Exception {

        if(sceltaLuogo){

            MealcalendarBean bean = new MealcalendarBean(dataselezionata, oraselezionata, SessionManagerSLT.getInstance().getLoggedInUsername(), ristorantescelto);
            MealcalendarController controller = new MealcalendarController(bean);
            controller.invioMail();

        }else{

            MealcalendarBean bean = new MealcalendarBean(dataselezionata, oraselezionata,SessionManagerSLT.getInstance().getLoggedInUsername(),ricettascelta);
            MealcalendarController controller = new MealcalendarController(bean);
            controller.invioMail();
        }

    }

    @FXML
    private void homeview(ActionEvent event) {
        Stage stage = (Stage) backhome.getScene().getWindow();
        GraphicController.cambiascena(stage, "usermenu-view.fxml");
    }

    private Callback<DatePicker, DateCell> disablePastDates() {
        return datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                // Disabilita le date passate
                if (item.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffaaaa;"); // Sfondo rosso chiaro per evidenziare
                }
            }
        };
    }
}
