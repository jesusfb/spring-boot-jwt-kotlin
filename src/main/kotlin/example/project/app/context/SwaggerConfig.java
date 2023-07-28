package example.project.app.config

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ToDo API")
                        .version("1.0")
                        .description("La API REST de ToDo es una aplicación sencilla que permite a los usuarios crear, leer, actualizar y eliminar tareas. \nEstá construida con Spring Boot 3.1 y proporciona una interfaz RESTful para interactuar con la aplicación.")
                        .termsOfService("http://www.apache.org/licenses/LICENSE-2.0")
                        .contact(new Contact()
                                .name("Lenin Quintero")
                                .email("leninquintero@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0")));
    }
}
