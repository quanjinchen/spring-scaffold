package dn.spring.scaffold.framework.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI javaProjectOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Spring Scaffold API")
                .version("1.0.0")
                .description("Spring scaffold template extracted from the iam project structure."));
    }
}
