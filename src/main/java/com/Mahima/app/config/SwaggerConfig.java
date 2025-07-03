package com.Mahima.app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .info(new Info()
                        .title("Tourism Ticketing and Diary App API")
                        .description("Backend APIs for Tourism, Ticket Booking, Diary, Packages")
                        .version("1.0.0"));
    }

    @Bean
    public GroupedOpenApi controllerApi() {
        return GroupedOpenApi.builder()
                .group("controllers")
                .packagesToScan("com.Mahima.app.controller") // Explicitly scan controller package
                .build();
    }
}