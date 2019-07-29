package com.spenceuk.cardealer.config;

import java.security.Timestamp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger {

  /**
   * Swagger documentation bean.
   *
   * @return a Swagger Docket.
   */
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("Car Dealer API")
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.spenceuk.cardealer.api.controller"))
        .build()
        .apiInfo(apiInfo())
        .directModelSubstitute(Timestamp.class, Long.class)
        .genericModelSubstitutes(ResponseEntity.class, Resource.class, Resources.class)
        .forCodeGeneration(true);
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("Car Dealer API")
        .description("Demonstration Car Dealer application API")
        .contact(new Contact("Chris Spence", "https://github.com/SpenceUK", ""))
        .license("MIT")
        .version("1.0-SNAPSHOT")
        .build();
  }
}