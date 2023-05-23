module com.example.signup {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.signup to javafx.fxml;
    exports com.example.signup;
    exports com.example.ConTroller;
    opens com.example.ConTroller to javafx.fxml;
}