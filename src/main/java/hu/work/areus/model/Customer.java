package hu.work.areus.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.work.areus.service.validator.Age;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

/**
 * Represents a customer entity.
 */
@Getter
@Setter
@Entity
public class Customer {

    /**
     * Unique identifier in database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * First name
     */
    @NotBlank(message = "First name cannot be empty.")
    private String firstName;

    /**
     * Last name
     */
    @NotBlank(message = "Last name cannot be empty.")
    private String lastName;

    /**
     * Date of birth
     */
    @Age
    private String birthDate;

    /**
     * E-mail address
     */
    @NotBlank(message = "Email address cannot be empty.")
    @Pattern(regexp = "^[^@]+@[^@]+\\.[^@]+$", message = "Invalid email address format.")
    private String email;

    /**
     * Phone number
     */
    @NotBlank(message = "Phone number cannot be empty.")
    @Pattern(regexp = "^\\+36[1-9][0-9]{8}$", message = "Invalid phone number format. Expected format: +36201234567")
    private String phoneNumber;

    /**
     * Calculated age based on the date of birth.
     */
    @Formula("YEAR(CURRENT_DATE()) - YEAR(PARSEDATETIME(birth_date, 'yyyy.MM.dd')) - " +
            "CASE WHEN MONTH(CURRENT_DATE()) < MONTH(PARSEDATETIME(birth_date, 'yyyy.MM.dd')) " +
            "OR (MONTH(CURRENT_DATE()) = MONTH(PARSEDATETIME(birth_date, 'yyyy.MM.dd')) " +
            "AND DAY(CURRENT_DATE()) < DAY(PARSEDATETIME(birth_date, 'yyyy.MM.dd'))) THEN 1 ELSE 0 END")
    @JsonIgnore
    private Integer age;
}
