package com.example.ConTroller;

import com.example.signup.DatabaseUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private Button Button_SignUp;

    @FXML
    private Button Button_LogIn;

    @FXML
    private RadioButton RadioButton_Creator;

    @FXML
    private RadioButton RadioButton_Consumer;

    @FXML
    private TextField TextField_Username;

    @FXML
    private TextField TextField_Password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton_Creator.setToggleGroup(toggleGroup);
        RadioButton_Consumer.setToggleGroup(toggleGroup);
        RadioButton_Creator.setSelected(true);
        Button_SignUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String username = TextField_Username.getText().trim();
                String password = TextField_Password.getText().trim();
                String toggleName = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
                if (!username.isEmpty() && !password.isEmpty()) {
                    DatabaseUtils.signUpUser(event, username, password);
                } else {
                    System.out.println("Please fill all information!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill all information!");
                    alert.show();
                }
            }
        });
        Button_LogIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DatabaseUtils.changeScene(event, "log-in.fxml", "Login!", "omar", "omar");
            }
        });
    }
}