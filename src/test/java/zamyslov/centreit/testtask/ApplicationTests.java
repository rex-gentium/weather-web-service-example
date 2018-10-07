package zamyslov.centreit.testtask;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zamyslov.centreit.testtask.controller.ServiceController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    ServiceController serviceController;

    static String urlGet = "http://localhost:8080/weather-api/get";
    static String urlPut = "http://localhost:8080/weather-api/put";
    static ObjectMapper objectMapper = null;

    @BeforeClass
    public static void init() {
        if (objectMapper != null) return;
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
    }

    @Test
    public void contextLoads() {
        assertThat(serviceController).isNotNull();
    }

}
