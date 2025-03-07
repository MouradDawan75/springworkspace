package fr.dawan.spring_rest_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){

        Server devServer = new Server();
        devServer.setUrl("http://localhost:8085");
        devServer.setDescription("Server URL in Dev. Environment");

        Server prodServer = new Server();
        prodServer.setUrl("http://localhost:8082");
        devServer.setDescription("Server URL in Prod. Environment");

        Contact contact = new Contact();
        contact.setEmail("contact@dawan.fr");
        contact.setUrl("wwww.dawan.fr/contact");
        contact.setName("Administrator");

        License license = new License().name("Dawan Licence").url("wwww.dawan.fr/licences/mit");

        Info info = new Info()
                .title("Tuto Open API")
                .description("Demo SPring REST Api")
                .version("1.0")
                .contact(contact)
                .termsOfService("wwww.dawan.fr/terms").license(license);


         return new OpenAPI().info(info).servers(List.of(devServer, prodServer));

    }
}
