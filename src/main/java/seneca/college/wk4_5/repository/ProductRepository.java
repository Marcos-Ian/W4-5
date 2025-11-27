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
package seneca.college.wk4_5.repository;

import javafx.collections.ObservableList;
import seneca.college.wk4_5.model.Product;

public interface ProductRepository {
    ObservableList<Product> findAll();

    void add(Product product);

    boolean remove(Product product);

    Product findById(int id);

    ObservableList<Product> findByName(String name);

    void replace(Product original, Product updated);

    void clear();

    int peekNextId();
}
