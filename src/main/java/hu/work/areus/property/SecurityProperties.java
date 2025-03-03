package hu.work.areus.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Represents the CORS properties read from the application.yaml file.
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "areus", ignoreUnknownFields = false)
public class SecurityProperties {
    private String[] cors = new String[0];
    private String username;
    private String password;
}
