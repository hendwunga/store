package com.hend.store.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${app.openapi.title:Store API}")
    private String apiTitle;

    @Value("${app.openapi.version:1.0.0}")
    private String apiVersion;

    @Value("${app.openapi.description:REST API untuk manajemen produk dan kategori}")
    private String apiDescription;

    @Value("${app.openapi.contact.name:Hend Wunga}")
    private String contactName;

    @Value("${app.openapi.contact.email:hend@example.com}")
    private String contactEmail;

    @Value("${app.openapi.license:MIT}")
    private String licenseName;

    @Value("${app.openapi.server.local:http://localhost:8080}")
    private String urlLocal;

    @Value("${app.openapi.server.staging:https://staging-store.herokuapp.com}")
    private String urlStaging;

    @Value("${app.openapi.server.prod:https://your-app.herokuapp.com}")
    private String urlProd;

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "basicAuth";

        return new OpenAPI()
            .info(new Info()
                .title(apiTitle)
                .version(apiVersion)
                .description(apiDescription)
                .contact(new Contact()
                    .name(contactName)
                    .email(contactEmail))
                .license(new License()
                    .name(licenseName)
                    .url("https://opensource.org/licenses/MIT")))
            .servers(List.of(
                new Server().url(urlLocal).description("Local Development Server"),
                new Server().url(urlStaging).description("Staging Server (Database Dummy - Aman untuk testing)"),
                new Server().url(urlProd).description("Production Server (Database Asli - HATI-HATI)")))
            .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
            .components(new Components()
                .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                    .name(securitySchemeName)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("basic")));
    }
}
