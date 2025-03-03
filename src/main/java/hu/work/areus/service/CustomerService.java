package hu.work.areus.service;

import hu.work.areus.model.Customer;
import hu.work.areus.repository.CustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing customers.
 */
@Service
public class CustomerService {

    @PersistenceContext
    private EntityManager entityManager;

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    /**
     * Get all customers.
     *
     * @return the list of customers
     */
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    /**
     * Get a customer by ID, if exists
     *
     * @param id the ID of the customer
     * @return the customer
     */
    public Customer getCustomerById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found."));
    }

    /**
     * Creates a new customer.
     *
     * @param customer the customer to create
     * @return the created customer
     */
    public Customer createCustomer(Customer customer) {
        return repository.save(customer);
    }

    /**
     * Updates a customer by ID, if possible
     *
     * @param id       the ID of the customer
     * @param customer the customer to update
     * @return the updated customer
     */
    public Customer updateCustomer(Long id, Customer customer) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found.");
        }
        customer.setId(id);
        return repository.save(customer);
    }

    /**
     * Deletes a customer by ID, if possible
     *
     * @param id the ID of the customer
     */
    public void deleteCustomer(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found.");
        }
        repository.deleteById(id);
    }

    /**
     * Get the average age of all customers.
     *
     * @return the average age
     */
    public Double getAverageAge() {
        return repository.getAverageAge();
    }

    /**
     * Get all customers between 18 and 40 years old.
     *
     * @return the list of customers
     */
    @Transactional
    public List<Customer> getCustomersBetween18And40() {
        List<Customer> customers = repository.findAll();
        customers.forEach(entityManager::refresh);
        return customers.stream()
                .filter(customer -> customer.getAge() != null && customer.getAge() >= 18 && customer.getAge() <= 40)
                .collect(Collectors.toList());
    }
}
