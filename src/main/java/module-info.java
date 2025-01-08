module com.example.mealcalendar {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.mealcalendar to javafx.fxml;
    exports com.example.mealcalendar;
}