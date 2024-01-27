package ecommerce.repository;

import ecommerce.model.Customer;
import stubs.database.InMemoryDatabase;

import java.util.Optional;
import java.util.UUID;

public class CustomerRepository {

    InMemoryDatabase<Customer> inMemoryDatabase;

    public CustomerRepository() {
        inMemoryDatabase = new InMemoryDatabase<>();
    }

    public Optional<Customer> findById(UUID id) {
        return inMemoryDatabase.findById(id);
    }

    public void save(Customer customer) {
        inMemoryDatabase.save(customer.getId(), customer);
    }
}
