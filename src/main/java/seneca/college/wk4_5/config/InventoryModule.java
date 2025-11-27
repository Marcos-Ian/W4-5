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
package seneca.college.wk4_5.config;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import seneca.college.wk4_5.repository.InMemoryPartRepository;
import seneca.college.wk4_5.repository.InMemoryProductRepository;
import seneca.college.wk4_5.repository.PartRepository;
import seneca.college.wk4_5.repository.ProductRepository;

public class InventoryModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(PartRepository.class).to(InMemoryPartRepository.class).in(Singleton.class);
        bind(ProductRepository.class).to(InMemoryProductRepository.class).in(Singleton.class);
    }
}
