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
package seneca.college.wk4_5.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory class manages all parts and products in the inventory management system.
 * Contains static methods for CRUD operations on parts and products.
 * Follows the UML Class Diagram specifications exactly.
 */
public class Inventory {

    // Private static ObservableList collections as per UML diagram
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds a new part to the inventory
     *
     * @param newPart The part to add to inventory
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a new product to the inventory
     *
     * @param newProduct The product to add to inventory
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Searches for a part by its ID
     *
     * @param partId The ID of the part to search for
     * @return The part if found, null otherwise
     */
    public static Part searchPartByID(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * Searches for a product by its ID
     *
     * @param productId The ID of the product to search for
     * @return The product if found, null otherwise
     */
    public static Product searchProductByID(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * Searches for parts by name (partial match, case-insensitive)
     *
     * @param name The name or partial name to search for
     * @return ObservableList of matching parts
     */
    public static ObservableList<Part> searchPartByName(String name) {
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().toLowerCase().contains(name.toLowerCase())) {
                matchingParts.add(part);
            }
        }

        return matchingParts;
    }

    /**
     * Searches for products by name (partial match, case-insensitive)
     *
     * @param name The name or partial name to search for
     * @return ObservableList of matching products
     */
    public static ObservableList<Product> searchProductByName(String name) {
        ObservableList<Product> matchingProducts = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(name.toLowerCase())) {
                matchingProducts.add(product);
            }
        }

        return matchingProducts;
    }

    /**
     * Updates a part at the specified index
     *
     * @param index The index of the part to update
     * @param selectedPart The new part data
     */
    public static void updatePart(int index, Part selectedPart) {
        if (index >= 0 && index < allParts.size()) {
            allParts.set(index, selectedPart);
        }
    }

    /**
     * Updates a product at the specified index
     *
     * @param index The index of the product to update
     * @param newProduct The new product data
     */
    public static void updateProduct(int index, Product newProduct) {
        if (index >= 0 && index < allProducts.size()) {
            allProducts.set(index, newProduct);
        }
    }

    /**
     * Deletes a part from the inventory
     *
     * @param selectedPart The part to delete
     * @return true if the part was successfully deleted, false otherwise
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * Deletes a product from the inventory
     *
     * @param selectedProduct The product to delete
     * @return true if the product was successfully deleted, false otherwise
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * Gets all parts in the inventory
     *
     * @return ObservableList of all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Gets all products in the inventory
     *
     * @return ObservableList of all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Gets the index of a specific part in the inventory
     *
     * @param part The part to find the index for
     * @return The index of the part, or -1 if not found
     */
    public static int getPartIndex(Part part) {
        return allParts.indexOf(part);
    }

    /**
     * Gets the index of a specific product in the inventory
     *
     * @param product The product to find the index for
     * @return The index of the product, or -1 if not found
     */
    public static int getProductIndex(Product product) {
        return allProducts.indexOf(product);
    }

    /**
     * Clears all parts from the inventory (useful for testing)
     */
    public static void clearAllParts() {
        allParts.clear();
    }

    /**
     * Clears all products from the inventory (useful for testing)
     */
    public static void clearAllProducts() {
        allProducts.clear();
    }
}