package zamyslov.centreit.testtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/***
 * Точка входа в приложение
 */
@SpringBootApplication
public class TesttaskApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TesttaskApplication.class, args);
    }
}
