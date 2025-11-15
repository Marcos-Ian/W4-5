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

    private final javafx.beans.property.IntegerProperty id;
    private final javafx.beans.property.StringProperty name;
    private final javafx.beans.property.DoubleProperty price;
    private final javafx.beans.property.IntegerProperty stock;
    private final javafx.beans.property.IntegerProperty min;
    private final javafx.beans.property.IntegerProperty max;
    private transient ObservableList<Part> associatedParts;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = new javafx.beans.property.SimpleIntegerProperty(id);
        this.name = new javafx.beans.property.SimpleStringProperty(name);
        this.price = new javafx.beans.property.SimpleDoubleProperty(price);
        this.stock = new javafx.beans.property.SimpleIntegerProperty(stock);
        this.min = new javafx.beans.property.SimpleIntegerProperty(min);
        this.max = new javafx.beans.property.SimpleIntegerProperty(max);
        this.associatedParts = FXCollections.observableArrayList();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public void setStock(int stock) {
        this.stock.set(stock);
    }

    public void setMin(int min) {
        this.min.set(min);
    }

    public void setMax(int max) {
        this.max.set(max);
    }

    public int getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public double getPrice() {
        return price.get();
    }

    public int getStock() {
        return stock.get();
    }

    public int getMin() {
        return min.get();
    }

    public int getMax() {
        return max.get();
    }

    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
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
