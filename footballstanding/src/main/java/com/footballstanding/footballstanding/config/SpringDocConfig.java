package com.footballstanding.footballstanding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SpringDocConfig {
    
    @Bean
  public OpenAPI footballStandingOpenAPI() {
      return new OpenAPI()
              .info(new Info().title("Football Standing App")
              .description("Football Standing application")
              .version("v0.0.1")
              .license(new License().name("Apache 2.0").url("http://springdoc.org")))
              .externalDocs(new ExternalDocumentation()
              .description("Football Standing Wiki Documentation")
              .url("https://footballstanding.wiki.github.org/docs"));
  }
}
