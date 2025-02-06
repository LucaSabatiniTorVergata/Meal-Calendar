module demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.desktop;
    requires java.logging;
    requires jbcrypt;

    exports com.example.mealcalendar;
    opens com.example.mealcalendar to javafx.fxml;
}