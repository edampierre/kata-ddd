package ecommerce.repository;

import ecommerce.model.Product;
import stubs.database.InMemoryDatabase;

import java.util.Optional;
import java.util.UUID;

public class ProductRepository {

    private InMemoryDatabase<Product> inMemoryDatabase;

    public ProductRepository() {
        inMemoryDatabase = new InMemoryDatabase<>();
    }

    public Optional<Product> findById(UUID id) {
        return inMemoryDatabase.findById(id);
    }

    public void save(Product product) {
        inMemoryDatabase.save(product.getId(), product);
    }
}
