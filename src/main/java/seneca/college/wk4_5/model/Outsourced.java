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
 * Outsourced class represents a part that is manufactured by an external company.
 * Extends the abstract Part class and adds company name functionality.
 */
public class Outsourced extends Part {

    private final javafx.beans.property.StringProperty companyName;

    /**
     * Constructor for Outsourced part as specified in UML diagram
     *
     * @param id          The unique identifier for the part
     * @param name        The name of the part
     * @param price       The price/cost of the part
     * @param stock       The current inventory/stock level
     * @param min         The minimum stock level allowed
     * @param max         The maximum stock level allowed
     * @param companyName The name of the company that manufactures this part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        // Call parent constructor
        super(id, name, price, stock, min, max);
        this.companyName = new javafx.beans.property.SimpleStringProperty(companyName);
    }

    /**
     * Sets the company name for this part
     *
     * @param companyName The company name to set
     */
    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    /**
     * Gets the company name for this part
     *
     * @return The company name
     */
    public String getCompanyName() {
        return companyName.get();
    }

    public javafx.beans.property.StringProperty companyNameProperty() {
        return companyName;
    }
}