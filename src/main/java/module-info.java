module demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires jbcrypt;
    requires jdk.jshell;
    requires jakarta.mail;
    requires com.google.protobuf;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires java.desktop;

    exports com.example.mealcalendar;
    opens com.example.mealcalendar to javafx.fxml;
    exports com.example.mealcalendar.register_login to javafx.fxml;
    opens com.example.mealcalendar.register_login to javafx.fxml;

}