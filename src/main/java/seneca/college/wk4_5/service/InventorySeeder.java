package seneca.college.wk4_5.service;

import seneca.college.wk4_5.model.InHouse;
import seneca.college.wk4_5.model.Outsourced;
import seneca.college.wk4_5.model.Part;
import seneca.college.wk4_5.model.Product;
import seneca.college.wk4_5.repository.PartRepository;
import seneca.college.wk4_5.repository.ProductRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.atomic.AtomicBoolean;

@Singleton
public class InventorySeeder {
    private final PartRepository partRepository;
    private final ProductRepository productRepository;
    private final AtomicBoolean seeded = new AtomicBoolean(false);

    @Inject
    public InventorySeeder(PartRepository partRepository, ProductRepository productRepository) {
        this.partRepository = partRepository;
        this.productRepository = productRepository;
    }

    public void seed() {
        if (seeded.get()) {
            return;
        }
        if (!partRepository.findAll().isEmpty() || !productRepository.findAll().isEmpty()) {
            seeded.set(true);
            return;
        }

        Part part1 = new InHouse(1001, "Brake Rotor", 15.99, 10, 5, 25, 101);
        Part part2 = new InHouse(1002, "Chain", 12.50, 15, 10, 30, 102);
        Part part3 = new Outsourced(1003, "Tire", 45.00, 8, 5, 20, "Tire Corp");
        Part part4 = new Outsourced(1004, "Seat", 35.99, 12, 8, 25, "Comfort Inc");
        Part part5 = new InHouse(1005, "Handlebar", 22.75, 20, 15, 40, 103);
        Part part6 = new InHouse(1006, "Pedal", 18.50, 25, 10, 50, 104);
        Part part7 = new Outsourced(1007, "Helmet", 55.00, 6, 3, 15, "Safety First");
        Part part8 = new InHouse(1008, "Gear System", 89.99, 7, 5, 20, 105);
        Part part9 = new Outsourced(1009, "Wheel Rim", 65.00, 10, 5, 30, "Rim Masters");
        Part part10 = new InHouse(1010, "Frame", 120.00, 5, 3, 15, 106);

        partRepository.add(part1);
        partRepository.add(part2);
        partRepository.add(part3);
        partRepository.add(part4);
        partRepository.add(part5);
        partRepository.add(part6);
        partRepository.add(part7);
        partRepository.add(part8);
        partRepository.add(part9);
        partRepository.add(part10);

        Product product1 = new Product(2001, "Mountain Bike", 299.99, 5, 2, 15);
        product1.addAssociatedPart(part1);
        product1.addAssociatedPart(part2);
        product1.addAssociatedPart(part3);

        Product product2 = new Product(2002, "Road Bike", 449.99, 8, 3, 20);
        product2.addAssociatedPart(part2);
        product2.addAssociatedPart(part3);
        product2.addAssociatedPart(part5);

        Product product3 = new Product(2003, "BMX Bike", 199.99, 12, 5, 25);
        product3.addAssociatedPart(part1);
        product3.addAssociatedPart(part6);

        Product product4 = new Product(2004, "Electric Bike", 899.99, 4, 2, 10);
        product4.addAssociatedPart(part8);
        product4.addAssociatedPart(part10);

        Product product5 = new Product(2005, "Kids Bike", 149.99, 15, 10, 30);
        product5.addAssociatedPart(part4);
        product5.addAssociatedPart(part6);

        Product product6 = new Product(2006, "Cruiser Bike", 249.99, 10, 5, 20);
        product6.addAssociatedPart(part3);
        product6.addAssociatedPart(part4);

        Product product7 = new Product(2007, "Folding Bike", 329.99, 7, 3, 15);
        product7.addAssociatedPart(part5);
        product7.addAssociatedPart(part9);

        Product product8 = new Product(2008, "Hybrid Bike", 379.99, 9, 4, 18);
        product8.addAssociatedPart(part2);
        product8.addAssociatedPart(part8);

        Product product9 = new Product(2009, "Tandem Bike", 499.99, 3, 1, 8);
        product9.addAssociatedPart(part10);
        product9.addAssociatedPart(part1);

        Product product10 = new Product(2010, "Tricycle", 179.99, 20, 10, 35);
        product10.addAssociatedPart(part4);
        product10.addAssociatedPart(part6);

        productRepository.add(product1);
        productRepository.add(product2);
        productRepository.add(product3);
        productRepository.add(product4);
        productRepository.add(product5);
        productRepository.add(product6);
        productRepository.add(product7);
        productRepository.add(product8);
        productRepository.add(product9);
        productRepository.add(product10);

        seeded.set(true);
    }
}
