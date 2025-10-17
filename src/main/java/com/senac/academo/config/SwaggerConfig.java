package com.senac.academo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Academo API")
                        .version("1.0.0")
                        .description("Sistema Acadêmico Mobile - API REST completa para gerenciamento acadêmico")
                        .contact(new Contact()
                                .name("Senac")
                                .email("suporte@academo.com")));
    }
}