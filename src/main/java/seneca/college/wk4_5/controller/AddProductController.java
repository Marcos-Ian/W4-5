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
import seneca.college.wk4_5.model.Part;
import seneca.college.wk4_5.model.Product;
import seneca.college.wk4_5.repository.PartRepository;
import seneca.college.wk4_5.repository.ProductRepository;

import jakarta.inject.Inject;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField invField;
    @FXML private TextField priceField;
    @FXML private TextField maxField;
    @FXML private TextField minField;

    @FXML private TextField partSearchField;
    @FXML private Button searchButton;
    @FXML private Button addPartButton;
    @FXML private Button removeAssociatedPartButton;

    @FXML private TableView<Part> availablePartsTable;
    @FXML private TableColumn<Part, Integer> availablePartIdColumn;
    @FXML private TableColumn<Part, String> availablePartNameColumn;
    @FXML private TableColumn<Part, Integer> availablePartInventoryColumn;
    @FXML private TableColumn<Part, Double> availablePartPriceColumn;

    @FXML private TableView<Part> associatedPartsTable;
    @FXML private TableColumn<Part, Integer> associatedPartIdColumn;
    @FXML private TableColumn<Part, String> associatedPartNameColumn;
    @FXML private TableColumn<Part, Integer> associatedPartInventoryColumn;
    @FXML private TableColumn<Part, Double> associatedPartPriceColumn;

    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private final PartRepository partRepository;
    private final ProductRepository productRepository;

    @Inject
    public AddProductController(PartRepository partRepository, ProductRepository productRepository) {
        this.partRepository = partRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idField.setText(String.valueOf(productRepository.peekNextId()));

        availablePartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        availablePartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        availablePartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        availablePartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        availablePartsTable.setItems(partRepository.findAll());
        associatedPartsTable.setItems(associatedParts);
        addValidationListeners();
    }

    @FXML
    void onSearchParts(ActionEvent event) {
        String searchText = partSearchField.getText().trim();

        if (searchText.isEmpty()) {
            availablePartsTable.setItems(partRepository.findAll());
            return;
        }

        ObservableList<Part> searchResults;
        try {
            int partId = Integer.parseInt(searchText);
            Part foundPart = partRepository.findById(partId);
            searchResults = FXCollections.observableArrayList();
            if (foundPart != null) {
                searchResults.add(foundPart);
            }
        } catch (NumberFormatException e) {
            searchResults = partRepository.findByName(searchText);
        }

        availablePartsTable.setItems(searchResults);

        if (searchResults.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Search Results", "No parts found matching your search criteria.");
        }
    }

    @FXML
    void onAddPart(ActionEvent event) {
        Part selectedPart = availablePartsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a part to add.");
            return;
        }

        if (associatedParts.contains(selectedPart)) {
            showAlert(Alert.AlertType.WARNING, "Duplicate Part", "This part is already associated with the product.");
            return;
        }

        associatedParts.add(selectedPart);
        showAlert(Alert.AlertType.INFORMATION, "Part Added", "Part successfully added to product.");
    }

    @FXML
    void onRemoveAssociatedPart(ActionEvent event) {
        Part selectedPart = associatedPartsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an associated part to remove.");
            return;
        }

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

    @FXML
    void onSave(ActionEvent event) {
        try {
            if (!validateInput()) {
                return;
            }

            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText().trim();
            int stock = Integer.parseInt(invField.getText());
            double price = Double.parseDouble(priceField.getText());
            int min = Integer.parseInt(minField.getText());
            int max = Integer.parseInt(maxField.getText());

            if (!validateBusinessRules(stock, min, max, price)) {
                return;
            }

            Product newProduct = new Product(id, name, price, stock, min, max);
            associatedParts.forEach(newProduct::addAssociatedPart);
            productRepository.add(newProduct);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Product added successfully!");
            closeWindow();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error",
                    "Please enter valid numeric values for ID, Inventory, Price, Min, and Max.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while saving the product: " + e.getMessage());
        }
    }

    @FXML
    void onCancel(ActionEvent event) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Cancel");
        confirmAlert.setHeaderText("Cancel Add Product");
        confirmAlert.setContentText("Are you sure you want to cancel? All entered data will be lost.");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            closeWindow();
        }
    }

    private boolean validateInput() {
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

    private boolean validateBusinessRules(int stock, int min, int max, double price) {
        if (min > max) {
            showAlert(Alert.AlertType.ERROR, "Validation Error",
                    "Minimum value cannot be greater than maximum value.");
            return false;
        }

        if (stock < min || stock > max) {
            showAlert(Alert.AlertType.ERROR, "Validation Error",
                    "Inventory level must be between minimum and maximum values.");
            return false;
        }

        if (min < 0 || max < 0 || stock < 0) {
            showAlert(Alert.AlertType.ERROR, "Validation Error",
                    "Minimum, Maximum, and Inventory values must be non-negative.");
            return false;
        }

        if (price < 0) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Price must be a positive value.");
            return false;
        }

        if (associatedParts.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error",
                    "A product must have at least one associated part.");
            return false;
        }

        double totalPartsCost = associatedParts.stream().mapToDouble(Part::getPrice).sum();
        if (price < totalPartsCost) {
            showAlert(Alert.AlertType.ERROR, "Validation Error",
                    String.format("Product price ($%.2f) cannot be less than the total cost of associated parts ($%.2f).",
                            price, totalPartsCost));
            return false;
        }

        return true;
    }

    private void addValidationListeners() {
        addNumericValidation(invField);
        addNumericValidation(minField);
        addNumericValidation(maxField);
        addDecimalValidation(priceField);
    }

    private void addNumericValidation(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void addDecimalValidation(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                textField.setText(oldValue);
            }
        });
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}
