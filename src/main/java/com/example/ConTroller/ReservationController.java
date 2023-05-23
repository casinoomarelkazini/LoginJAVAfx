package com.example.ConTroller;

import com.example.DOA.ReservationDAO;
import com.example.module.Reservation;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class ReservationController {
    @FXML
    private TextField userIdField;
    @FXML
    private TextField terrainIdField;
    @FXML
    private DatePicker datePicker;

    private ReservationDAO reservationDAO;

    public ReservationController() {
        reservationDAO = new ReservationDAO();
    }

    @FXML
    private void submitReservation() {
        try {
            int userId = Integer.parseInt(userIdField.getText());
            int terrainId = Integer.parseInt(terrainIdField.getText());
            LocalDate reservationDate = datePicker.getValue();

            Reservation reservation = new Reservation(userId, terrainId, reservationDate);
            reservationDAO.insertReservation(reservation);

            showSuccessAlert("Reservation created successfully!");
            clearFields();
        } catch (NumberFormatException e) {
            showErrorAlert("Invalid input! User ID and Terrain ID must be numeric.");
        } catch (Exception e) {
            showErrorAlert("An error occurred while creating the reservation.");
            e.printStackTrace();
        }
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        userIdField.clear();
        terrainIdField.clear();
        datePicker.setValue(null);
    }
}
