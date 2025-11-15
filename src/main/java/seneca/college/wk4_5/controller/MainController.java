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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import seneca.college.wk4_5.model.Part;
import seneca.college.wk4_5.model.Product;
import seneca.college.wk4_5.repository.PartRepository;
import seneca.college.wk4_5.repository.ProductRepository;
import seneca.college.wk4_5.service.InventorySeeder;
import seneca.college.wk4_5.util.ViewLoader;

import javax.inject.Inject;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * MainController handles the main screen functionality for the Inventory Management System.
 * This includes managing parts and products, search functionality, and navigation to other screens.
 */
public class MainController implements Initializable {

    // Parts Table and Controls
    @FXML private TextField partSearchField;
    @FXML private Button partSearchButton;
    @FXML private TableView<Part> partsTable;
    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInventoryColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;
    @FXML private Button addPartButton;
    @FXML private Button modifyPartButton;
    @FXML private Button deletePartButton;

    // Products Table and Controls
    @FXML private TextField productSearchField;
    @FXML private Button productSearchButton;
    @FXML private TableView<Product> productsTable;
    @FXML private TableColumn<Product, Integer> productIdColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Integer> productInventoryColumn;
    @FXML private TableColumn<Product, Double> productPriceColumn;
    @FXML private Button addProductButton;
    @FXML private Button modifyProductButton;
    @FXML private Button deleteProductButton;

    // Exit Button
    @FXML private Button exitButton;

    private final PartRepository partRepository;
    private final ProductRepository productRepository;
    private final InventorySeeder inventorySeeder;
    private final ViewLoader viewLoader;

    @Inject
    public MainController(PartRepository partRepository,
                          ProductRepository productRepository,
                          InventorySeeder inventorySeeder,
                          ViewLoader viewLoader) {
        this.partRepository = partRepository;
        this.productRepository = productRepository;
        this.inventorySeeder = inventorySeeder;
        this.viewLoader = viewLoader;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize Parts Table columns
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Initialize Products Table columns
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        inventorySeeder.seed();
        partsTable.setItems(partRepository.findAll());
        productsTable.setItems(productRepository.findAll());
    }

    /**
     * Handles the Part Search button action.
     * Searches by ID (if numeric) or by name (partial match, case-insensitive).
     */
    @FXML
    void onPartSearch(ActionEvent event) {
        String searchText = partSearchField.getText().trim();

        // If search field is empty, show all parts
        if (searchText.isEmpty()) {
            partsTable.setItems(partRepository.findAll());
            return;
        }

        ObservableList<Part> searchResults = FXCollections.observableArrayList();

        // Try to search by ID first
        try {
            int partId = Integer.parseInt(searchText);
            Part foundPart = partRepository.findById(partId);
            if (foundPart != null) {
                searchResults.add(foundPart);
            }
        } catch (NumberFormatException e) {
            // If not a number, search by name
            searchResults = partRepository.findByName(searchText);
        }

        partsTable.setItems(searchResults);

        // Show message if no results found
        if (searchResults.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Search Results",
                    "No parts found matching your search criteria.");
        }
    }

    /**
     * Handles the Add Part button action.
     * Opens the Add Part screen.
     */
    @FXML
    void onAddPart(ActionEvent event) {
        try {
            openWindow("/seneca/college/wk4_5/addPartForm.fxml", "Add Part", 400, 500);
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Could not open Add Part screen.");
            e.printStackTrace();
        }
    }

    /**
     * Handles the Modify Part button action.
     * Opens the Modify Part screen with selected part data.
     */
    @FXML
    void onModifyPart(ActionEvent event) {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection",
                    "Please select a part to modify.");
            return;
        }

        try {
            FXMLLoader loader = viewLoader.load("/seneca/college/wk4_5/modifyPartForm.fxml");
            Parent root = loader.getRoot();

            // Pass the selected part to the controller
            ModifyPartController controller = loader.getController();
            controller.initializeWithPart(selectedPart);

            // Show the window
            Stage stage = new Stage();
            stage.setTitle("Modify Part");
            stage.setScene(new Scene(root, 400, 500));
            stage.show();

        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Could not open Modify Part screen.");
            e.printStackTrace();
        }
    }

    /**
     * Handles the Delete Part button action.
     * Deletes the selected part after confirmation.
     */
    @FXML
    void onDeletePart(ActionEvent event) {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection",
                    "Please select a part to delete.");
            return;
        }

        // Confirmation dialog (required by workshop)
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText("Delete Part");
        confirmAlert.setContentText("Are you sure you want to delete the part: " +
                selectedPart.getName() + "?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean deleted = partRepository.remove(selectedPart);
            if (deleted) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Part deleted successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Delete Failed",
                        "Could not delete the selected part.");
            }
        }
    }

    /**
     * Handles the Product Search button action.
     * Searches by ID (if numeric) or by name (partial match, case-insensitive).
     */
    @FXML
    void onProductSearch(ActionEvent event) {
        String searchText = productSearchField.getText().trim();

        // If search field is empty, show all products
        if (searchText.isEmpty()) {
            productsTable.setItems(productRepository.findAll());
            return;
        }

        ObservableList<Product> searchResults = FXCollections.observableArrayList();

        // Try to search by ID first
        try {
            int productId = Integer.parseInt(searchText);
            Product foundProduct = productRepository.findById(productId);
            if (foundProduct != null) {
                searchResults.add(foundProduct);
            }
        } catch (NumberFormatException e) {
            // If not a number, search by name
            searchResults = productRepository.findByName(searchText);
        }

        productsTable.setItems(searchResults);

        // Show message if no results found
        if (searchResults.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Search Results",
                    "No products found matching your search criteria.");
        }
    }

    /**
     * Handles the Add Product button action.
     * Opens the Add Product screen.
     */
    @FXML
    void onAddProduct(ActionEvent event) {
        try {
            openWindow("/seneca/college/wk4_5/addProductForm.fxml", "Add Product", 900, 600);
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Could not open Add Product screen.");
            e.printStackTrace();
        }
    }

    /**
     * Handles the Modify Product button action.
     * Opens the Modify Product screen with selected product data.
     */
    @FXML
    void onModifyProduct(ActionEvent event) {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection",
                    "Please select a product to modify.");
            return;
        }

        try {
            FXMLLoader loader = viewLoader.load("/seneca/college/wk4_5/modifyProductForm.fxml");
            Parent root = loader.getRoot();

            // Pass the selected product to the controller
            ModifyProductController controller = loader.getController();
            controller.initializeWithProduct(selectedProduct);

            // Show the window
            Stage stage = new Stage();
            stage.setTitle("Modify Product");
            stage.setScene(new Scene(root, 900, 600));
            stage.show();

        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Could not open Modify Product screen.");
            e.printStackTrace();
        }
    }

    /**
     * Handles the Delete Product button action.
     * Validates that product has no associated parts before deletion.
     * Required validation: Cannot delete product with associated parts.
     */
    @FXML
    void onDeleteProduct(ActionEvent event) {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection",
                    "Please select a product to delete.");
            return;
        }

        // REQUIRED VALIDATION: Check if product has associated parts
        // Confirmation dialog (required by workshop)
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText("Delete Product");
        confirmAlert.setContentText("Are you sure you want to delete the product: " +
                selectedProduct.getName() + "?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean deleted = productRepository.remove(selectedProduct);
                if (deleted) {
                    showAlert(Alert.AlertType.INFORMATION, "Success",
                            "Product deleted successfully.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Delete Failed",
                            "Could not delete the selected product.");
                }
            } catch (IllegalStateException ex) {
                showAlert(Alert.AlertType.ERROR, "Cannot Delete", ex.getMessage());
            }
        }
    }

    /**
     * Handles the Exit button action.
     * Closes the application after confirmation.
     */
    @FXML
    void onExit(ActionEvent event) {
        // Confirmation dialog for exit (required by workshop)
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Exit Application");
        confirmAlert.setHeaderText("Confirm Exit");
        confirmAlert.setContentText("Are you sure you want to exit the application?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    /**
     * Helper method to open a new window.
     *
     * @param fxmlPath Path to the FXML file
     * @param title Window title
     * @param width Window width
     * @param height Window height
     * @throws IOException If FXML file cannot be loaded
     */
    private void openWindow(String fxmlPath, String title, int width, int height)
            throws IOException {
        FXMLLoader loader = viewLoader.load(fxmlPath);
        Parent root = loader.getRoot();

        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root, width, height));
        stage.show();
    }

    /**
     * Helper method to show alert dialogs.
     *
     * @param alertType Type of alert
     * @param title Alert title
     * @param message Alert message
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}