package configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
/**
 * Scanning the project for all forms and functionality to build the app
 * @author danolszewski
 *
 */
@Configuration
@ComponentScan({ "controllers", "services" })
public class AppConfig {

}
