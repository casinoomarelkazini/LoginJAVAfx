package com.example.DOA;
import com.example.module.Reservation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/user-database";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    // Insert a new reservation into the database
    public void insertReservation(Reservation reservation) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "INSERT INTO reservations (user_id, terrain_id, reservation_date, start_time, end_time) " +
                    "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, reservation.getUserId());
            statement.setInt(2, reservation.getTerrainId());
            statement.setObject(3, reservation.getReservationDate());
            statement.setObject(4, reservation.getStartTime());
            statement.setObject(5, reservation.getEndTime());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve existing reservations from the database
    public List<Reservation> getReservations() {
        List<Reservation> reservations = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT * FROM reservations";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int reservationId = resultSet.getInt("reservation_id");
                int userId = resultSet.getInt("user_id");
                int terrainId = resultSet.getInt("terrain_id");
                LocalDate reservationDate = resultSet.getDate("reservation_date").toLocalDate();
                LocalTime startTime = resultSet.getTime("start_time").toLocalTime();
                LocalTime endTime = resultSet.getTime("end_time").toLocalTime();

                Reservation reservation = new Reservation(reservationId, userId, terrainId, reservationDate,
                        startTime, endTime);
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }
}