package hu.work.areus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Main class of the application. Made for the Areus application process.
 */
@SpringBootApplication
@ConfigurationPropertiesScan("hu.work.areus.property")
public class AreusApplication {

    public static void main(String[] args) {
        SpringApplication.run(AreusApplication.class, args);
    }
}
