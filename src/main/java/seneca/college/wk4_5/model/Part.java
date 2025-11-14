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

    // Private instance variables as per UML diagram
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

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
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    // Setter methods as specified in UML

    /**
     * Sets the ID of the part
     *
     * @param id The ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the name of the part
     *
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the price of the part
     *
     * @param price The price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the stock level of the part
     *
     * @param stock The stock level to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Sets the minimum stock level of the part
     *
     * @param min The minimum stock level to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Sets the maximum stock level of the part
     *
     * @param max The maximum stock level to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Gets the ID of the part
     *
     * @return The part ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the part
     *
     * @return The part name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the price of the part
     *
     * @return The part price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the stock level of the part
     *
     * @return The current stock level
     */
    public int getStock() {
        return stock;
    }

    /**
     * Gets the minimum stock level of the part
     *
     * @return The minimum stock level
     */
    public int getMin() {
        return min;
    }

    /**
     * Gets the maximum stock level of the part
     *
     * @return The maximum stock level
     */
    public int getMax() {
        return max;
    }
}