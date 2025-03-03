package hu.work.areus.util;

import hu.work.areus.model.Customer;
import hu.work.areus.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Initializes the database with some default customers.
 */
@Component
public class CustomerInitializer implements CommandLineRunner {

    private final CustomerRepository repository;

    public CustomerInitializer(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {

        Customer customer1 = new Customer();
        customer1.setFirstName("Peter");
        customer1.setLastName("Areus");
        customer1.setBirthDate("1974.12.05");
        customer1.setEmail("areuspeter@gmail.com");
        customer1.setPhoneNumber("+36201234567");
        repository.save(customer1);

        Customer customer2 = new Customer();
        customer2.setFirstName("Luca");
        customer2.setLastName("Areus");
        customer2.setBirthDate("2005.02.11");
        customer2.setEmail("areusluca@gmail.com");
        customer2.setPhoneNumber("+36206789012");
        repository.save(customer2);

        Customer customer3 = new Customer();
        customer3.setFirstName("Balazs");
        customer3.setLastName("Areus");
        customer3.setEmail("areusbalazs@gmail.com");
        customer3.setBirthDate("1999.01.20");
        customer3.setPhoneNumber("+36204296789");
        repository.save(customer3);
    }
}
