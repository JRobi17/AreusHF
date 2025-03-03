package hu.work.areus.controller;

import hu.work.areus.model.Customer;
import hu.work.areus.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing customers.
 */
@RestController
@RequestMapping(
        path = "/areus/customers",
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    /**
     * Get a customer by ID.
     *
     * @param id the ID of the customer
     * @return the customer
     */
    @GetMapping("/getCustomer/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return service.getCustomerById(id);
    }

    /**
     * Get all customers.
     *
     * @return the list of customers
     */
    @GetMapping("/getAllCustomers")
    public List<Customer> getAllCustomers() {
        return service.getAllCustomers();
    }

    /**
     * Creates a new customer.
     *
     * @param customer the customer to create
     * @return the created customer
     */
    @PostMapping("/createCustomer")
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        return service.createCustomer(customer);
    }

    /**
     * Updates a customer by id.
     *
     * @param id       the ID of the customer
     * @param customer the customer to update
     * @return the updated customer
     */
    @PutMapping("/updateCustomer/{id}")
    public Customer updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer customer) {
        return service.updateCustomer(id, customer);
    }

    /**
     * Deletes a customer by id.
     *
     * @param id the ID of the customer
     */
    @DeleteMapping("/deleteCustomer/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        service.deleteCustomer(id);
    }

    /**
     * Get the average age of all customers.
     *
     * @return the average age
     */
    @GetMapping("/getCustomersAverageAge")
    public Double getAverageAge() {
        return service.getAverageAge();
    }

    /**
     * Get all customers between 18 and 40 years old.
     *
     * @return the list of customers
     */
    @GetMapping("/getAllCustomersBetween18And40")
    public List<Customer> getCustomersBetween18And40() {
        return service.getCustomersBetween18And40();
    }
}
