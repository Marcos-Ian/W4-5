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
