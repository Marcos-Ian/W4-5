/**********************************************
 Workshop #4-5
 Course:APD545 - S5
 Last Name:Araujo Carneiro
 First Name:Marcos Ian
 ID:153220223
 Section:NCC
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature
 Date:05-11-2025
 **********************************************/
package seneca.college.wk4_5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seneca.college.wk4_5.controller.LoginController;

/**
 * Main application class for the Inventory Management System.
 * Updated to start with login screen first.
 */
public class InventoryManagementApp extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        // Start with login screen instead of main screen
        showLoginScreen();
    }

    /**
     * Shows the login screen first
     */
    private void showLoginScreen() {
        try {
            // Use the path that works - Path 2 from your debug output
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            // Get the controller and pass reference to this main app
            LoginController loginController = loader.getController();
            loginController.setMainApp(this);

            // Set up the login stage
            primaryStage.setTitle("Inventory Management System - Login");
            primaryStage.setScene(new Scene(root, 450, 400));
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
            primaryStage.show();

        } catch (Exception e) {
            System.err.println("Error loading login screen: " + e.getMessage());
            e.printStackTrace();

            // Show error dialog
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Application Error");
            alert.setHeaderText("Could not start application");
            alert.setContentText("Error: " + e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Shows the main inventory management screen after successful login
     */
    public void showMainScreen() {
        try {
            // Use the path that works - Path 2 from your debug output
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            Parent root = loader.load();

            // Update the primary stage to show main screen
            primaryStage.setTitle("Inventory Management System");
            primaryStage.setScene(new Scene(root, 1200, 600));
            primaryStage.setResizable(true);
            primaryStage.centerOnScreen();

        } catch (Exception e) {
            System.err.println("Error loading main screen: " + e.getMessage());
            e.printStackTrace();

            // Show error dialog
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Application Error");
            alert.setHeaderText("Could not load main screen");
            alert.setContentText("Error: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}