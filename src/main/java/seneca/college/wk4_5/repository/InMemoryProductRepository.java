package seneca.college.wk4_5.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seneca.college.wk4_5.model.Product;

import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryProductRepository implements ProductRepository {
    private final ObservableList<Product> products = FXCollections.observableArrayList();
    private final AtomicInteger nextId = new AtomicInteger(2000);

    @Override
    public ObservableList<Product> findAll() {
        return products;
    }

    @Override
    public void add(Product product) {
        products.add(product);
        bumpSequence(product.getId());
    }

    @Override
    public boolean remove(Product product) {
        if (product == null) {
            return false;
        }
        if (!product.getAllAssociatedParts().isEmpty()) {
            throw new IllegalStateException("Cannot delete a product while it still has associated parts");
        }
        return products.remove(product);
    }

    @Override
    public Product findById(int id) {
        return products.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    @Override
    public ObservableList<Product> findByName(String name) {
        String lowered = name.toLowerCase();
        ObservableList<Product> matches = FXCollections.observableArrayList();
        products.stream()
                .filter(product -> product.getName().toLowerCase().contains(lowered))
                .forEach(matches::add);
        return matches;
    }

    @Override
    public void replace(Product original, Product updated) {
        int index = products.indexOf(original);
        if (index >= 0) {
            products.set(index, updated);
            bumpSequence(updated.getId());
        }
    }

    @Override
    public void clear() {
        products.clear();
        nextId.set(2000);
    }

    @Override
    public int peekNextId() {
        return nextId.get();
    }

    private void bumpSequence(int idUsed) {
        nextId.accumulateAndGet(idUsed + 1, Math::max);
    }
}
