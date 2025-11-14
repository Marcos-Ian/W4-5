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
package seneca.college.wk4_5.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seneca.college.wk4_5.InventoryManagementApp;

import java.util.HashMap;
import java.util.Map;

/**
 * LoginController handles the login screen functionality.
 * Updated to work with the main application screen navigation.
 */
public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    // Reference to the main application
    private InventoryManagementApp mainApp;

    private final Map<String, String> users = new HashMap<>();

    public LoginController() {
        // Two hardcoded users
        users.put("admin", "admin123");
        users.put("user", "user123");
    }

    /**
     * Sets the reference to the main application
     * This method is called by the main app to establish the connection
     */
    public void setMainApp(InventoryManagementApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        // Validate input
        if (username.isEmpty()) {
            showAlert("Validation Error", "Please enter a username", Alert.AlertType.WARNING);
            return;
        }

        if (password.isEmpty()) {
            showAlert("Validation Error", "Please enter a password", Alert.AlertType.WARNING);
            return;
        }

        // Check credentials
        if (users.containsKey(username) && users.get(username).equals(password)) {
            showAlert("Login Successful", "Welcome, " + username + "!", Alert.AlertType.INFORMATION);

            // Navigate to main screen
            if (mainApp != null) {
                mainApp.showMainScreen();
            } else {
                showAlert("Application Error", "Could not load main screen. Please restart the application.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Login Failed", "Invalid username or password", Alert.AlertType.ERROR);
            // Clear password field on failed login
            passwordField.clear();
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}