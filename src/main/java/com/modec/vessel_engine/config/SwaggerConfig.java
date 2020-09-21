package com.modec.vessel_engine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.modec.vessel_engine.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }


    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Modec's Hard Skills Backend Test Submission",
                "This is my submission for Modec's hard skills backend test."
                        .concat("I opted for a SpringBoot application because the requirements did not involve a large amount of businness logic,")
                        .concat("and as such my solution greatly benefits from SpringBoot's out of the box functionalities.")
                        .concat("Code was written and implemented on an as-needed basis, prioritizing simplicity and agility,")
                        .concat("without compromising the future of the product.")
                        .concat("I opted for H2 as using an in-memory database seemed sufficient for the project's requirements and usage."),
                "API TOS",
                "Terms of service",
                new Contact("Guilherme Simas", "github.com/guilhermesimas", "guilherme_abinader@hotmail.com"),
                "License", "license-url", Collections.emptyList()
        );
    }
}
