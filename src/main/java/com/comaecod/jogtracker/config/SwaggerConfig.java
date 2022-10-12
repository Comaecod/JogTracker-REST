package com.comaecod.jogtracker.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	
	private ApiKey apiKeys() {
		return new ApiKey("JWT", AppConstantsDefaults.AUTHORIZATION_HEADER, "header");
	}
	
	private List<SecurityReference> securityReferences(){
		AuthorizationScope scope = new AuthorizationScope("global", "accesseverything");
		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] {scope}));
	}
	
	private List<SecurityContext> securityContexts() {
		return Arrays.asList(SecurityContext.builder().securityReferences(securityReferences()).build());
	}
	
	@Bean
	public Docket api() {
				
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getInfo())
				.securityContexts(securityContexts())
				.securitySchemes(Arrays.asList(apiKeys()))
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo getInfo() {
		return new ApiInfo("JogTracker(REST)", 
				"This project is developed using Springboot", 
				"1.0.0", 
				"T&C", 
				new Contact("Vishnu","vishnu.com", "vishnuthecoder@gmail.com"), 
				"License of @comaecod", 
				"API License api.comaecod",
				java.util.Collections.EMPTY_LIST);
	}
}
