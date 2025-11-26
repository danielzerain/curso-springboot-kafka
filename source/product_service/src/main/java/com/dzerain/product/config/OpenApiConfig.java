package com.dzerain.product.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Productos")
                        .version("1.0.0")
                        .description("Documentación de la API para el sistema de ecommerce.")
                        .termsOfService("http://swagger.io/terms/")
                        .contact(new Contact()
                                .name("Soporte Técnico")
                                .email("danielz@outlook.com")
                                .url("https://danielzerain.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .servers(List.of(
                        new Server().url("http://localhost:8000").description("Servidor Local")
                ));
    }

    @Bean
    public GroupedOpenApi productApi() {
        return GroupedOpenApi.builder().group("Categorias").pathsToMatch("/api/categories/**").build();
    }

    @Bean
    public GroupedOpenApi categoryApi() {
        return GroupedOpenApi.builder().group("Productos").pathsToMatch("/api/products/**").build();
    }



}
