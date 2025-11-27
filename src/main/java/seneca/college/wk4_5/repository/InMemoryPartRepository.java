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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seneca.college.wk4_5.model.Part;

import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryPartRepository implements PartRepository {
    private final ObservableList<Part> parts = FXCollections.observableArrayList();
    private final AtomicInteger nextId = new AtomicInteger(1000);

    @Override
    public ObservableList<Part> findAll() {
        return parts;
    }

    @Override
    public void add(Part part) {
        parts.add(part);
        bumpSequence(part.getId());
    }

    @Override
    public boolean remove(Part part) {
        return parts.remove(part);
    }

    @Override
    public Part findById(int id) {
        return parts.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    @Override
    public ObservableList<Part> findByName(String name) {
        String lowered = name.toLowerCase();
        ObservableList<Part> matches = FXCollections.observableArrayList();
        parts.stream()
                .filter(part -> part.getName().toLowerCase().contains(lowered))
                .forEach(matches::add);
        return matches;
    }

    @Override
    public void replace(Part original, Part updated) {
        int index = parts.indexOf(original);
        if (index >= 0) {
            parts.set(index, updated);
            bumpSequence(updated.getId());
        }
    }

    @Override
    public void clear() {
        parts.clear();
        nextId.set(1000);
    }

    @Override
    public int peekNextId() {
        return nextId.get();
    }

    private void bumpSequence(int idUsed) {
        nextId.accumulateAndGet(idUsed + 1, Math::max);
    }
}
