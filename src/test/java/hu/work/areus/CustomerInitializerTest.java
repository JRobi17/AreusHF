package hu.work.areus;

import hu.work.areus.model.Customer;
import hu.work.areus.repository.CustomerRepository;
import hu.work.areus.util.CustomerInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for {@link CustomerInitializer}.
 */
@DataJpaTest
class CustomerInitializerTest {

    private CustomerInitializer customerInitializer;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerInitializer = new CustomerInitializer(customerRepository);
    }

    @Test
    void testRun() {
        customerInitializer.run();

        List<Customer> customers = customerRepository.findAll();
        assertEquals(3, customers.size());

        assertCustomer(customers.get(0), "Peter", "1974.12.05", "areuspeter@gmail.com", "+36201234567");
        assertCustomer(customers.get(1), "Luca", "2005.02.11", "areusluca@gmail.com", "+36206789012");
        assertCustomer(customers.get(2), "Balazs", "1999.01.20", "areusbalazs@gmail.com", "+36204296789");
    }

    private void assertCustomer(Customer customer, String firstName, String birthDate, String email, String phoneNumber) {
        assertEquals(firstName, customer.getFirstName());
        assertEquals("Areus", customer.getLastName());
        assertEquals(birthDate, customer.getBirthDate());
        assertEquals(email, customer.getEmail());
        assertEquals(phoneNumber, customer.getPhoneNumber());
    }
}

