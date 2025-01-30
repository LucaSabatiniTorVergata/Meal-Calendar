module demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    exports com.example.mealcalendar;
    opens com.example.mealcalendar to javafx.fxml;
}