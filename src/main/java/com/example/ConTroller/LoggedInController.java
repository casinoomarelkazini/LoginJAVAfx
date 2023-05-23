package com.example.signup;

import com.example.DOA.ReservationDAO;
import com.example.module.Reservation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable{

    @FXML
    private Button Button_Logout;
    @FXML
    private TextField userIdField;
    @FXML
    private TextField terrainIdField;
    @FXML
    private DatePicker datePicker;

    private ReservationDAO reservationDAO;
    @FXML
    private Label Label_Welcome;

    @FXML
    private Label Label_Status;


   /* public void initialize(URL location, ResourceBundle resources) {
        Button_Logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DatabaseUtils.changeScene(event, "log-in.fxml", "Log in!", null, null);
            } 
        });
    }*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reservationDAO = new ReservationDAO();
        // Other initialization code
    }
    public void setStatusInformation(String username, String status){
        Label_Welcome.setText("Welcome "+username+"!");
        Label_Status.setText(" "+status+" channel");
    }
    @FXML
    private void submitReservation() {
        try {
            int userId = Integer.parseInt(userIdField.getText());
            int terrainId = Integer.parseInt(terrainIdField.getText());
            LocalDate reservationDate = datePicker.getValue();

            Reservation reservation = new Reservation(userId, terrainId, reservationDate);
            reservationDAO.insertReservation(reservation);
            if (reservationDAO != null) {
                reservationDAO.insertReservation(reservation);
            } else {
                throw new Exception("reservationDAO is not initialized");
            }
            showSuccessAlert("Reservation created successfully!");
            clearFields();
        } catch (NumberFormatException e) {
            showErrorAlert("Invalid input! User ID and Terrain ID must be numeric.");
        } catch (Exception e) {
            showErrorAlert("An error occurred while creating the reservation.");
            e.printStackTrace();
        }
    }
    @FXML
    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void clearFields() {
        userIdField.clear();
        terrainIdField.clear();
        // Clear any other fields you may have
    }
}
