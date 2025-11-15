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


/**
 * Abstract Part class representing a part in the inventory management system.
 * This class serves as the base class for InHouse and Outsourced parts.
 * Follows the UML Class Diagram specifications exactly.
 */
public abstract class Part {

    private final javafx.beans.property.IntegerProperty id;
    private final javafx.beans.property.StringProperty name;
    private final javafx.beans.property.DoubleProperty price;
    private final javafx.beans.property.IntegerProperty stock;
    private final javafx.beans.property.IntegerProperty min;
    private final javafx.beans.property.IntegerProperty max;

    /**
     * Constructor for Part class as specified in UML diagram
     *
     * @param id    The unique identifier for the part
     * @param name  The name of the part
     * @param price The price/cost of the part
     * @param stock The current inventory/stock level
     * @param min   The minimum stock level allowed
     * @param max   The maximum stock level allowed
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = new javafx.beans.property.SimpleIntegerProperty(id);
        this.name = new javafx.beans.property.SimpleStringProperty(name);
        this.price = new javafx.beans.property.SimpleDoubleProperty(price);
        this.stock = new javafx.beans.property.SimpleIntegerProperty(stock);
        this.min = new javafx.beans.property.SimpleIntegerProperty(min);
        this.max = new javafx.beans.property.SimpleIntegerProperty(max);
    }

    // Setter methods as specified in UML

    /**
     * Sets the ID of the part
     *
     * @param id The ID to set
     */
    public void setId(int id) {
        this.id.set(id);
    }

    /**
     * Sets the name of the part
     *
     * @param name The name to set
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Sets the price of the part
     *
     * @param price The price to set
     */
    public void setPrice(double price) {
        this.price.set(price);
    }

    /**
     * Sets the stock level of the part
     *
     * @param stock The stock level to set
     */
    public void setStock(int stock) {
        this.stock.set(stock);
    }

    /**
     * Sets the minimum stock level of the part
     *
     * @param min The minimum stock level to set
     */
    public void setMin(int min) {
        this.min.set(min);
    }

    /**
     * Sets the maximum stock level of the part
     *
     * @param max The maximum stock level to set
     */
    public void setMax(int max) {
        this.max.set(max);
    }

    /**
     * Gets the ID of the part
     *
     * @return The part ID
     */
    public int getId() {
        return id.get();
    }

    /**
     * Gets the name of the part
     *
     * @return The part name
     */
    public String getName() {
        return name.get();
    }

    /**
     * Gets the price of the part
     *
     * @return The part price
     */
    public double getPrice() {
        return price.get();
    }

    /**
     * Gets the stock level of the part
     *
     * @return The current stock level
     */
    public int getStock() {
        return stock.get();
    }

    /**
     * Gets the minimum stock level of the part
     *
     * @return The minimum stock level
     */
    public int getMin() {
        return min.get();
    }

    /**
     * Gets the maximum stock level of the part
     *
     * @return The maximum stock level
     */
    public int getMax() {
        return max.get();
    }

    public javafx.beans.property.IntegerProperty idProperty() {
        return id;
    }

    public javafx.beans.property.StringProperty nameProperty() {
        return name;
    }

    public javafx.beans.property.DoubleProperty priceProperty() {
        return price;
    }

    public javafx.beans.property.IntegerProperty stockProperty() {
        return stock;
    }

    public javafx.beans.property.IntegerProperty minProperty() {
        return min;
    }

    public javafx.beans.property.IntegerProperty maxProperty() {
        return max;
    }
}