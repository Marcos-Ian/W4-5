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
 * Product class represents a product in the inventory management system.
 * Products can have associated parts and maintain their own inventory information.
 * Follows the UML Class Diagram specifications exactly.
 */
public class Product {

    private static final long serialVersionUID = 1L;

    // Private instance variables as per UML diagram
    // Note: ObservableList is not serializable, so we need custom serialization
    private transient ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor for Product class as specified in UML diagram
     *
     * @param id    The unique identifier for the product
     * @param name  The name of the product
     * @param price The price of the product
     * @param stock The current inventory/stock level
     * @param min   The minimum stock level allowed
     * @param max   The maximum stock level allowed
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.associatedParts = FXCollections.observableArrayList();
    }

    // Setter methods as specified in UML diagram

    /**
     * Sets the ID of the product
     *
     * @param id The ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the name of the product
     *
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the price of the product
     *
     * @param price The price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the stock level of the product
     *
     * @param stock The stock level to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Sets the minimum stock level of the product
     *
     * @param min The minimum stock level to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Sets the maximum stock level of the product
     *
     * @param max The maximum stock level to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    // Getter methods as specified in UML diagram

    /**
     * Gets the ID of the product
     *
     * @return The product ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the product
     *
     * @return The product name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the price of the product
     *
     * @return The product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the stock level of the product
     *
     * @return The current stock level
     */
    public int getStock() {
        return stock;
    }

    /**
     * Gets the minimum stock level of the product
     *
     * @return The minimum stock level
     */
    public int getMin() {
        return min;
    }

    /**
     * Gets the maximum stock level of the product
     *
     * @return The maximum stock level
     */
    public int getMax() {
        return max;
    }

    // Associated parts methods as specified in UML diagram

    /**
     * Adds an associated part to this product
     *
     * @param part The part to associate with this product
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Removes/disassociates a part from this product
     *
     * @param selectedAssociatedPart The part to remove from this product
     * @return true if the part was successfully removed, false otherwise
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        boolean removed = associatedParts.remove(selectedAssociatedPart);

        return removed;
    }

    /**
     * Gets all associated parts for this product
     *
     * @return ObservableList of all associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }



}