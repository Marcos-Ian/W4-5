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
import seneca.college.wk4_5.model.*;

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

        // Load sample data (required by workshop - minimum 10 products, 1 part per product)
        loadSampleData();

        // Bind tables to inventory data
        partsTable.setItems(Inventory.getAllParts());
        productsTable.setItems(Inventory.getAllProducts());
    }

    /**
     * Loads sample data into the inventory as required by the workshop.
     * Requirement: Minimum 10 products with at least 1 part per product.
     */
    private void loadSampleData() {
        // Clear any existing data
        Inventory.clearAllParts();
        Inventory.clearAllProducts();

        // Create sample parts - varied InHouse and Outsourced parts
        InHouse part1 = new InHouse(1001, "Brake Rotor", 15.99, 10, 5, 25, 101);
        InHouse part2 = new InHouse(1002, "Chain", 12.50, 15, 10, 30, 102);
        Outsourced part3 = new Outsourced(1003, "Tire", 45.00, 8, 5, 20, "Tire Corp");
        Outsourced part4 = new Outsourced(1004, "Seat", 35.99, 12, 8, 25, "Comfort Inc");
        InHouse part5 = new InHouse(1005, "Handlebar", 22.75, 20, 15, 40, 103);
        InHouse part6 = new InHouse(1006, "Pedal", 18.50, 25, 10, 50, 104);
        Outsourced part7 = new Outsourced(1007, "Helmet", 55.00, 6, 3, 15, "Safety First");
        InHouse part8 = new InHouse(1008, "Gear System", 89.99, 7, 5, 20, 105);
        Outsourced part9 = new Outsourced(1009, "Wheel Rim", 65.00, 10, 5, 30, "Rim Masters");
        InHouse part10 = new InHouse(1010, "Frame", 120.00, 5, 3, 15, 106);

        // Add parts to inventory
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);
        Inventory.addPart(part6);
        Inventory.addPart(part7);
        Inventory.addPart(part8);
        Inventory.addPart(part9);
        Inventory.addPart(part10);

        // Update next part ID counter
        AddPartController.setNextPartId(1011);

        // Create 10 sample products (requirement)
        // Each product must have at least one associated part
        Product product1 = new Product(2001, "Mountain Bike", 299.99, 5, 2, 15);
        product1.addAssociatedPart(part1);
        product1.addAssociatedPart(part2);
        product1.addAssociatedPart(part3);

        Product product2 = new Product(2002, "Road Bike", 449.99, 8, 3, 20);
        product2.addAssociatedPart(part2);
        product2.addAssociatedPart(part3);
        product2.addAssociatedPart(part5);

        Product product3 = new Product(2003, "BMX Bike", 199.99, 12, 5, 25);
        product3.addAssociatedPart(part1);
        product3.addAssociatedPart(part6);

        Product product4 = new Product(2004, "Electric Bike", 899.99, 4, 2, 10);
        product4.addAssociatedPart(part8);
        product4.addAssociatedPart(part10);

        Product product5 = new Product(2005, "Kids Bike", 149.99, 15, 10, 30);
        product5.addAssociatedPart(part4);
        product5.addAssociatedPart(part6);

        Product product6 = new Product(2006, "Cruiser Bike", 249.99, 10, 5, 20);
        product6.addAssociatedPart(part3);
        product6.addAssociatedPart(part4);

        Product product7 = new Product(2007, "Folding Bike", 329.99, 7, 3, 15);
        product7.addAssociatedPart(part5);
        product7.addAssociatedPart(part9);

        Product product8 = new Product(2008, "Hybrid Bike", 379.99, 9, 4, 18);
        product8.addAssociatedPart(part2);
        product8.addAssociatedPart(part8);

        Product product9 = new Product(2009, "Tandem Bike", 499.99, 3, 1, 8);
        product9.addAssociatedPart(part10);
        product9.addAssociatedPart(part1);

        Product product10 = new Product(2010, "Tricycle", 179.99, 20, 10, 35);
        product10.addAssociatedPart(part4);
        product10.addAssociatedPart(part6);

        // Add products to inventory
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        Inventory.addProduct(product4);
        Inventory.addProduct(product5);
        Inventory.addProduct(product6);
        Inventory.addProduct(product7);
        Inventory.addProduct(product8);
        Inventory.addProduct(product9);
        Inventory.addProduct(product10);

        // Update next product ID counter
        AddProductController.setNextProductId(2011);

        System.out.println("Sample data loaded: " + Inventory.getAllParts().size() +
                " parts, " + Inventory.getAllProducts().size() + " products");
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
            partsTable.setItems(Inventory.getAllParts());
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
            openWindow("/addPartForm.fxml", "Add Part", 400, 500);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifyPartForm.fxml"));
            Parent root = loader.load();

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
            boolean deleted = Inventory.deletePart(selectedPart);
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
            productsTable.setItems(Inventory.getAllProducts());
            return;
        }

        ObservableList<Product> searchResults = FXCollections.observableArrayList();

        // Try to search by ID first
        try {
            int productId = Integer.parseInt(searchText);
            Product foundProduct = Inventory.searchProductByID(productId);
            if (foundProduct != null) {
                searchResults.add(foundProduct);
            }
        } catch (NumberFormatException e) {
            // If not a number, search by name
            searchResults = Inventory.searchProductByName(searchText);
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
            openWindow("/addProductForm.fxml", "Add Product", 900, 600);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifyProductForm.fxml"));
            Parent root = loader.load();

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
        if (!selectedProduct.getAllAssociatedParts().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Cannot Delete",
                    "Cannot delete a product that has associated parts. " +
                            "Please remove all associated parts first.");
            return;
        }

        // Confirmation dialog (required by workshop)
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText("Delete Product");
        confirmAlert.setContentText("Are you sure you want to delete the product: " +
                selectedProduct.getName() + "?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean deleted = Inventory.deleteProduct(selectedProduct);
            if (deleted) {
                showAlert(Alert.AlertType.INFORMATION, "Success",
                        "Product deleted successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Delete Failed",
                        "Could not delete the selected product.");
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

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