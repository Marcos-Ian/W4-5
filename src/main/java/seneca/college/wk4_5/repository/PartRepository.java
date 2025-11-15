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
