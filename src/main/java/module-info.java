module demo {
    requires javafx.controls;
    requires javafx.fxml;



    exports com.example.mealcalendar;
    opens com.example.mealcalendar to javafx.fxml;
}