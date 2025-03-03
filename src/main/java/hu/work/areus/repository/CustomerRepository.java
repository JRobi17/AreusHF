package hu.work.areus.repository;

import hu.work.areus.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository interface for {@link Customer} entities.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT AVG(c.age) FROM Customer c")
    Double getAverageAge();
}
