package com.example.signup;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.example.ConTroller.LoggedInController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class DatabaseUtils {

    private static String retrievedStatus;

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, String status){
        Parent root = null;
        if(username != null && status != null){
            try{
                FXMLLoader loader = new FXMLLoader(DatabaseUtils.class.getResource(fxmlFile));
                root = loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setStatusInformation(username, status);
            }catch(IOException e){
                e.printStackTrace();
            }
        }else{
            try{
                root = FXMLLoader.load(DatabaseUtils.class.getResource(fxmlFile));
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }


    public static void signUpUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatementInsert = null;
        PreparedStatement preparedStatementCheckUserExists = null;
        ResultSet resultSet = null;
        String user_id = UUID.randomUUID().toString();

        try {
            // Conection M3A BD
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user-database", "root", "root");

            // Check if the username already exists
            preparedStatementCheckUserExists = connection.prepareStatement("SELECT * FROM user WHERE username = ?");
            preparedStatementCheckUserExists.setString(1, username);
            resultSet = preparedStatementCheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                // Username is already taken
                System.out.println("Username is already taken");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username is already taken");
                alert.show();
            } else {
                // Insert the new user
                preparedStatementInsert =  connection.prepareStatement("INSERT INTO user (user_id, username, password) VALUES (?, ?, ?)");
                preparedStatementInsert.setString(1, user_id);
                preparedStatementInsert.setString(2, username);
                preparedStatementInsert.setString(3, password);
                preparedStatementInsert.executeUpdate();
                preparedStatementInsert.close();

                // Retrieve the user_id of the newly inserted user
                PreparedStatement preparedStatementGetUserID = connection.prepareStatement("SELECT user_id FROM user WHERE username = ?");
                preparedStatementGetUserID.setString(1, username);
                ResultSet resultSetUserID = preparedStatementGetUserID.executeQuery();

                if (resultSetUserID.next()) {
                    int userId = resultSetUserID.getInt("user_id");
                    resultSetUserID.close();
                    preparedStatementGetUserID.close();

                    // Close the database connection
                    connection.close();

                    // Perform further actions with the user_id
                    changeScene(event, "reservation.fxml", "Welcome!", username, String.valueOf(userId));
                } else {
                    System.out.println("Failed to retrieve user_id");
                    // Handle the error case when user_id cannot be retrieved
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException
        } finally {
            // Close the ResultSets, PreparedStatements, and Connection
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (preparedStatementCheckUserExists != null) {
                try {
                    preparedStatementCheckUserExists.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (preparedStatementInsert != null) {
                try {
                    preparedStatementInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }









/*
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user-database", "root", "root");
            preparedStatementCheckUserExists = connection.prepareStatement("SELECT * FROM user WHERE username= ?");
            preparedStatementCheckUserExists.setString(1, username);
            resultSet = preparedStatementCheckUserExists.executeQuery();
            if(resultSet.isBeforeFirst()){
                System.out.println("Username is already taken");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username is already taken");
                alert.show();
            }else{
                preparedStatementInsert = connection.prepareStatement("INSERT INTO user (user_id,username, password) VALUES (?, ?, ?)");
                preparedStatementInsert.setString(1, user_id);
                preparedStatementInsert.setString(2, username);
                preparedStatementInsert.setString(3, password);
                preparedStatementInsert.executeUpdate();
                changeScene(event, "reservation.fxml", "Welcome!", username, status);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            if(resultSet != null){
                try{
                    resultSet.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(preparedStatementCheckUserExists != null){
                try{
                    preparedStatementCheckUserExists.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(preparedStatementInsert !=null){
                try{
                    preparedStatementInsert.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection !=null){
                try{
                    connection.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }*/


    public static void logInUser(ActionEvent event, String username, String password){

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user-database", "root", "root");
            preparedStatement = connection.prepareStatement("SELECT password, status FROM user WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                String status = resultSet.getString("status");

                if (password.equals(storedPassword)) {
                    System.out.println("Login successful!");
                    changeScene(event, "logged-in.fxml", "Welcome", username, retrievedStatus);

                    // Perform further actions for successful login
                    // For example, you can call another method or update UI accordingly
                } else {
                    System.out.println("Incorrect password!");
                    // Display an error message or handle incorrect password scenario
                }
            } else {
                System.out.println("User not found!");
                // Display an error message or handle user not found scenario
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }





























        /*Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user-database", "root", "root");
            preparedStatement = connection.prepareStatement("SELECT password, status FROM user WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.isBeforeFirst()){
                System.out.println("Username don't exist!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username don't exist!");
                alert.show();
            }else{
                while(resultSet.next()){
                    String retrievedPassword = resultSet.getString("password");
                    String retrievedStatus = resultSet.getString("status");
                    if(retrievedPassword.equals(password)){
                        changeScene(event, "logged-in.fxml", "Welcome", username, retrievedStatus);
                    }else{
                        System.out.println("Password is incorret!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Password is incorret!");
                        alert.show();
                    }
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            if(resultSet != null){
                try{
                    resultSet.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }    
            if(preparedStatement != null){
                try{
                    preparedStatement.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection !=null){
                try{
                    connection.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }*/

}
