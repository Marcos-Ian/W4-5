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
import seneca.college.wk4_5.model.Part;

/**
 * Repository abstraction for interacting with Part aggregates.
 */
public interface PartRepository {
    ObservableList<Part> findAll();

    void add(Part part);

    boolean remove(Part part);

    Part findById(int id);

    ObservableList<Part> findByName(String name);

    void replace(Part original, Part updated);

    void clear();

    int peekNextId();
}
