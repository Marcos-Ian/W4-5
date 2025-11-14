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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import seneca.college.wk4_5.model.Inventory;
import seneca.college.wk4_5.model.Part;
import seneca.college.wk4_5.model.Product;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * AddProductController handles the Add Product screen functionality.
 * Allows users to create new products and associate parts with them.
 */
public class AddProductController implements Initializable {

    // Product information fields
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField invField;
    @FXML private TextField priceField;
    @FXML private TextField maxField;
    @FXML private TextField minField;

    // Parts search and management
    @FXML private TextField partSearchField;
    @FXML private Button searchButton;
    @FXML private Button addPartButton;
    @FXML private Button removeAssociatedPartButton;

    // Available parts table
    @FXML private TableView<Part> availablePartsTable;
    @FXML private TableColumn<Part, Integer> availablePartIdColumn;
    @FXML private TableColumn<Part, String> availablePartNameColumn;
    @FXML private TableColumn<Part, Integer> availablePartInventoryColumn;
    @FXML private TableColumn<Part, Double> availablePartPriceColumn;

    // Associated parts table
    @FXML private TableView<Part> associatedPartsTable;
    @FXML private TableColumn<Part, Integer> associatedPartIdColumn;
    @FXML private TableColumn<Part, String> associatedPartNameColumn;
    @FXML private TableColumn<Part, Integer> associatedPartInventoryColumn;
    @FXML private TableColumn<Part, Double> associatedPartPriceColumn;

    // Action buttons
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    // Data collections
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    // Static variable to track next available ID
    private static int nextProductId = 2000; // Starting from 2000 for products

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set auto-generated ID
        idField.setText(String.valueOf(getNextProductId()));

        // Initialize Available Parts Table
        availablePartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        availablePartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        availablePartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        availablePartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Initialize Associated Parts Table
        associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Load all parts initially
        availablePartsTable.setItems(Inventory.getAllParts());

        // Set associated parts table
        associatedPartsTable.setItems(associatedParts);

        // Add input validation listeners
        addValidationListeners();
    }

    /**
     * Handles the search parts action
     */
    @FXML
    void onSearchParts(ActionEvent event) {
        String searchText = partSearchField.getText().trim();

        if (searchText.isEmpty()) {
            availablePartsTable.setItems(Inventory.getAllParts());
            return;
        }

        ObservableList<Part> searchResults = FXCollections.observableArrayList();

        // Try to search by ID first
        try {
            int partId = Integer.parseInt(searchText);
            Part foundPart = Inventory.searchPartByID(partId);
            if (foundPart != null) {
                searchResults.add(foundPart);
            }
        } catch (NumberFormatException e) {
            // If not a number, search by name
            searchResults = Inventory.searchPartByName(searchText);
        }

        availablePartsTable.setItems(searchResults);

        if (searchResults.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Search Results", "No parts found matching your search criteria.");
        }
    }

    /**
     * Handles adding a part to the product
     */
    @FXML
    void onAddPart(ActionEvent event) {
        Part selectedPart = availablePartsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a part to add.");
            return;
        }

        // Check if part is already associated
        if (associatedParts.contains(selectedPart)) {
            showAlert(Alert.AlertType.WARNING, "Duplicate Part", "This part is already associated with the product.");
            return;
        }

        // Add to associated parts
        associatedParts.add(selectedPart);
        showAlert(Alert.AlertType.INFORMATION, "Part Added", "Part successfully added to product.");
    }

    /**
     * Handles removing an associated part from the product
     */
    @FXML
    void onRemoveAssociatedPart(ActionEvent event) {
        Part selectedPart = associatedPartsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an associated part to remove.");
            return;
        }

        // Confirmation dialog
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Remove");
        confirmAlert.setHeaderText("Remove Associated Part");
        confirmAlert.setContentText("Are you sure you want to remove this part from the product?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            associatedParts.remove(selectedPart);
            showAlert(Alert.AlertType.INFORMATION, "Part Removed", "Part successfully removed from product.");
        }
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
            if (!validateBusinessRules(stock, min, max, price)) {
                return;
            }

            // Create new product
            Product newProduct = new Product(id, name, price, stock, min, max);

            // Add all associated parts
            for (Part part : associatedParts) {
                newProduct.addAssociatedPart(part);
            }

            // Add to inventory
            Inventory.addProduct(newProduct);

            // Update the next product ID
            nextProductId++;

            // Show success message
            showAlert(Alert.AlertType.INFORMATION, "Success", "Product added successfully!");

            // Close this window
            closeWindow();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error",
                    "Please enter valid numeric values for ID, Inventory, Price, Min, and Max.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while saving the product: " + e.getMessage());
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
        confirmAlert.setHeaderText("Cancel Add Product");
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
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Product name is required.");
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

        // Validate numeric fields
        try {
            Integer.parseInt(invField.getText().trim());
            Double.parseDouble(priceField.getText().trim());
            Integer.parseInt(minField.getText().trim());
            Integer.parseInt(maxField.getText().trim());
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
    private boolean validateBusinessRules(int stock, int min, int max, double price) {
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

        if (price < 0) {
            showAlert(Alert.AlertType.ERROR, "Validation Error",
                    "Price must be a positive value.");
            return false;
        }

        // Check that product has at least one associated part
        if (associatedParts.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error",
                    "A product must have at least one associated part.");
            return false;
        }

        // Check that product price is not less than cost of parts
        double totalPartsCost = 0.0;
        for (Part part : associatedParts) {
            totalPartsCost += part.getPrice();
        }

        if (price < totalPartsCost) {
            showAlert(Alert.AlertType.ERROR, "Validation Error",
                    String.format("Product price ($%.2f) cannot be less than the total cost of associated parts ($%.2f).",
                            price, totalPartsCost));
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

    /**
     * Gets the next available product ID
     */
    public static int getNextProductId() {
        return nextProductId;
    }

    /**
     * Sets the next product ID (useful for initialization)
     */
    public static void setNextProductId(int id) {
        nextProductId = id;
    }
}