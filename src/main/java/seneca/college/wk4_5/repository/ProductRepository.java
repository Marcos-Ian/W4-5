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
