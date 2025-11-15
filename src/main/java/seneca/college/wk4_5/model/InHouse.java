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
 * InHouse class represents a part that is manufactured in-house.
 * Extends the abstract Part class and adds machine ID functionality.
 */
public class InHouse extends Part {

    private final javafx.beans.property.IntegerProperty machineId;

    /**
     * Constructor for InHouse part as specified in UML diagram
     *
     * @param id        The unique identifier for the part
     * @param name      The name of the part
     * @param price     The price/cost of the part
     * @param stock     The current inventory/stock level
     * @param min       The minimum stock level allowed
     * @param max       The maximum stock level allowed
     * @param machineId The machine ID used to manufacture this part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        // Call parent constructor
        super(id, name, price, stock, min, max);
        this.machineId = new javafx.beans.property.SimpleIntegerProperty(machineId);
    }

    /**
     * Sets the machine ID for this part
     *
     * @param machineId The machine ID to set
     */
    public void setMachine(int machineId) {
        this.machineId.set(machineId);
    }

    /**
     * Gets the machine ID for this part
     *
     * @return The machine ID
     */
    public int getMachine() {
        return machineId.get();
    }

    public javafx.beans.property.IntegerProperty machineIdProperty() {
        return machineId;
    }
}