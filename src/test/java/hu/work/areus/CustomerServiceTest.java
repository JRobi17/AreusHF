package hu.work.areus;

import hu.work.areus.model.Customer;
import hu.work.areus.repository.CustomerRepository;
import hu.work.areus.service.CustomerService;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for {@link CustomerService}.
 */
@DataJpaTest
class CustomerServiceTest extends CustomerTest {

    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        customerService = new CustomerService(customerRepository);
        customerRepository.deleteAll();
        injectEntityManager(customerService, testEntityManager.getEntityManager());
    }

    private void injectEntityManager(CustomerService service, EntityManager entityManager) {
        try {
            Field field = CustomerService.class.getDeclaredField("entityManager");
            field.setAccessible(true);
            field.set(service, entityManager);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject EntityManager", e);
        }
    }

    @Test
    void testGetAllCustomers() {
        customerRepository.save(createDefaultCustomer1());
        customerRepository.save(createDefaultCustomer2());
        customerRepository.save(createDefaultCustomer3());
        List<Customer> customers = customerService.getAllCustomers();
        assertEquals(3, customers.size());
    }

    @Test
    void testGetCustomerByIdWithExistingCustomer() {
        Customer customer = customerRepository.save(createDefaultCustomer1());
        Customer foundCustomer = customerService.getCustomerById(customer.getId());
        assertEquals("Peter", foundCustomer.getFirstName());
    }

    @Test
    void testGetCustomerByIdWithNonExistingCustomer() {
        customerRepository.save(createDefaultCustomer1());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                customerService.getCustomerById(5L));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void testCreateCustomer() {
        Customer customer = createDefaultCustomer1();
        Customer savedCustomer = customerService.createCustomer(customer);
        assertNotNull(savedCustomer.getId());
        assertEquals("Peter", savedCustomer.getFirstName());
    }

    @Test
    void testCreateCustomerUnder18() {
        Customer customer = createDefaultCustomer1();
        customer.setBirthDate("2010.05.01");
        assertThrows(ConstraintViolationException.class, () ->
                customerService.createCustomer(customer));
    }

    @Test
    void testCreateCustomerWithNoBirthDate() {
        Customer customer = createDefaultCustomer1();
        customer.setBirthDate(null);
        assertThrows(ConstraintViolationException.class, () ->
                customerService.createCustomer(customer));
    }

    @Test
    void testCreateCustomerWithInvalidBirthDate() {
        Customer customer = createDefaultCustomer1();
        customer.setBirthDate("once upon a time");
        assertThrows(ConstraintViolationException.class, () ->
                customerService.createCustomer(customer));
    }

    @Test
    void testUpdateCustomer() {
        Customer customer = customerRepository.save(createDefaultCustomer1());
        String newEmail = "robert@gmail.com";
        customer.setEmail(newEmail);
        Customer updatedCustomer = customerService.updateCustomer(customer.getId(), customer);
        assertEquals(newEmail, updatedCustomer.getEmail());
    }

    @Test
    void testUpdateCustomerWithInvalidData() {
        Customer customer = customerRepository.save(createDefaultCustomer1());
        customer.setEmail("invalidEmail");
        assertThrows(ConstraintViolationException.class, () ->
                customerService.updateCustomer(customer.getId(), customer));
    }

    @Test
    void testUpdateCustomerNonExisting() {
        Customer customer = createDefaultCustomer1();
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                customerService.updateCustomer(5L, customer));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }


    @Test
    void testDeleteCustomer() {
        Customer customer = customerRepository.save(createDefaultCustomer1());
        customerService.deleteCustomer(customer.getId());
        assertFalse(customerRepository.existsById(customer.getId()));
    }

    @Test
    void testDeleteCustomerNonExisting() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                customerService.deleteCustomer(5L));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void testGetAverageAge() {
        customerRepository.save(createDefaultCustomer1());
        customerRepository.save(createDefaultCustomer2());
        customerRepository.save(createDefaultCustomer3());
        Double avgAge = customerService.getAverageAge();
        assertEquals(32.0, avgAge);
    }

    @Test
    void testGetCustomersBetween18And40() {
        customerRepository.save(createDefaultCustomer1());
        customerRepository.save(createDefaultCustomer2());
        customerRepository.save(createDefaultCustomer3());
        testEntityManager.flush();
        List<Customer> filteredCustomers = customerService.getCustomersBetween18And40();
        assertEquals(2, filteredCustomers.size());
    }


}
