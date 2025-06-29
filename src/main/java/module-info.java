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
    exports com.example.mealcalendar.login;
    opens com.example.mealcalendar.login to javafx.fxml;
    exports com.example.mealcalendar.findrest;
    opens com.example.mealcalendar.findrest to javafx.fxml;
    exports com.example.mealcalendar.findrecipe;
    opens com.example.mealcalendar.findrecipe to javafx.fxml;
    exports com.example.mealcalendar.fillfridge;
    opens com.example.mealcalendar.fillfridge to javafx.fxml;
    exports com.example.mealcalendar.seteatingtime;
    opens com.example.mealcalendar.seteatingtime to javafx.fxml;
    exports com.example.mealcalendar.makediet;
    opens com.example.mealcalendar.makediet to javafx.fxml;
    exports com.example.mealcalendar.followdiet to javafx.fxml;
    opens com.example.mealcalendar.followdiet to javafx.fxml;
}