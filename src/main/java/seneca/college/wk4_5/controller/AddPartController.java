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
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import seneca.college.wk4_5.model.InHouse;
import seneca.college.wk4_5.model.Outsourced;
import seneca.college.wk4_5.model.Part;
import seneca.college.wk4_5.repository.PartRepository;

import javax.inject.Inject;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * AddPartController handles the Add Part screen functionality.
 * Allows users to create new InHouse or Outsourced parts and add them to inventory.
 */
public class AddPartController implements Initializable {

    // Radio buttons for part type selection
    @FXML private RadioButton inHouseRadio;
    @FXML private RadioButton outsourcedRadio;
    @FXML private ToggleGroup partTypeGroup;

    // Text fields for part data
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField invField;
    @FXML private TextField priceField;
    @FXML private TextField maxField;
    @FXML private TextField minField;
    @FXML private TextField machineCompanyField;

    // Dynamic label that changes based on part type
    @FXML private Label machineCompanyLabel;

    // Action buttons
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private final PartRepository partRepository;

    @Inject
    public AddPartController(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idField.setText(String.valueOf(partRepository.peekNextId()));

        // Set initial state for In-House (default selection)
        updateFieldsForPartType();

        // Add input validation listeners
        addValidationListeners();
    }

    /**
     * Handles radio button selection change between In-House and Outsourced
     */
    @FXML
    void onPartTypeChange(ActionEvent event) {
        updateFieldsForPartType();
    }

    /**
     * Updates the label and prompt text based on selected part type
     */
    private void updateFieldsForPartType() {
        if (inHouseRadio.isSelected()) {
            machineCompanyLabel.setText("Machine ID:");
            machineCompanyField.setPromptText("Enter Machine ID");
        } else {
            machineCompanyLabel.setText("Company Name:");
            machineCompanyField.setPromptText("Enter Company Name");
        }
        // Clear the field when switching types
        machineCompanyField.clear();
    }

    /**
     * Handles the Save button action
     */
    @FXML
    void onSave(ActionEvent event) {
        try {
            // Validate all input fields
            if (!validateInput()) {
                return;
            }

            // Parse input values
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText().trim();
            int stock = Integer.parseInt(invField.getText());
            double price = Double.parseDouble(priceField.getText());
            int min = Integer.parseInt(minField.getText());
            int max = Integer.parseInt(maxField.getText());

            // Validate business rules
            if (!validateBusinessRules(stock, min, max)) {
                return;
            }

            // Create the appropriate part type
            Part newPart;
            if (inHouseRadio.isSelected()) {
                int machineId = Integer.parseInt(machineCompanyField.getText());
                newPart = new InHouse(id, name, price, stock, min, max, machineId);
            } else {
                String companyName = machineCompanyField.getText().trim();
                newPart = new Outsourced(id, name, price, stock, min, max, companyName);
            }

            partRepository.add(newPart);

            // Show success message
            showAlert(Alert.AlertType.INFORMATION, "Success", "Part added successfully!");

            // Close this window
            closeWindow();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error",
                    "Please enter valid numeric values for ID, Inventory, Price, Min, Max, and Machine ID (if applicable).");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while saving the part: " + e.getMessage());
        }
    }

    /**
     * Handles the Cancel button action
     */
    @FXML
    void onCancel(ActionEvent event) {
        // Show confirmation dialog
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Cancel");
        confirmAlert.setHeaderText("Cancel Add Part");
        confirmAlert.setContentText("Are you sure you want to cancel? All entered data will be lost.");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            closeWindow();
        }
    }

    /**
     * Validates all input fields
     */
    private boolean validateInput() {
        // Check if required fields are empty
        if (nameField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Part name is required.");
            return false;
        }

        if (invField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Inventory level is required.");
            return false;
        }

        if (priceField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Price is required.");
            return false;
        }

        if (minField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Minimum value is required.");
            return false;
        }

        if (maxField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Maximum value is required.");
            return false;
        }

        if (machineCompanyField.getText().trim().isEmpty()) {
            String fieldType = inHouseRadio.isSelected() ? "Machine ID" : "Company Name";
            showAlert(Alert.AlertType.ERROR, "Validation Error", fieldType + " is required.");
            return false;
        }

        // Validate numeric fields
        try {
            Integer.parseInt(invField.getText());
            Double.parseDouble(priceField.getText());
            Integer.parseInt(minField.getText());
            Integer.parseInt(maxField.getText());

            if (inHouseRadio.isSelected()) {
                Integer.parseInt(machineCompanyField.getText());
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error",
                    "Please enter valid numeric values for all numeric fields.");
            return false;
        }

        return true;
    }

    /**
     * Validates business rules
     */
    private boolean validateBusinessRules(int stock, int min, int max) {
        // Check min <= max
        if (min > max) {
            showAlert(Alert.AlertType.ERROR, "Validation Error",
                    "Minimum value cannot be greater than maximum value.");
            return false;
        }

        // Check min <= stock <= max
        if (stock < min || stock > max) {
            showAlert(Alert.AlertType.ERROR, "Validation Error",
                    "Inventory level must be between minimum and maximum values.");
            return false;
        }

        // Check positive values
        if (min < 0 || max < 0 || stock < 0) {
            showAlert(Alert.AlertType.ERROR, "Validation Error",
                    "Minimum, Maximum, and Inventory values must be non-negative.");
            return false;
        }

        double price = Double.parseDouble(priceField.getText());
        if (price < 0) {
            showAlert(Alert.AlertType.ERROR, "Validation Error",
                    "Price must be a positive value.");
            return false;
        }

        return true;
    }

    /**
     * Adds input validation listeners to numeric fields
     */
    private void addValidationListeners() {
        // Add listeners to numeric fields to allow only numbers
        addNumericValidation(invField);
        addNumericValidation(minField);
        addNumericValidation(maxField);
        addDecimalValidation(priceField);

        // Machine ID field should only accept numbers when In-House is selected
        machineCompanyField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (inHouseRadio.isSelected() && !newValue.matches("\\d*")) {
                machineCompanyField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    /**
     * Adds numeric validation to a text field (integers only)
     */
    private void addNumericValidation(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    /**
     * Adds decimal validation to a text field (allows decimal numbers)
     */
    private void addDecimalValidation(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                textField.setText(oldValue);
            }
        });
    }

    /**
     * Shows an alert dialog
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Closes the current window
     */
    private void closeWindow() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

}