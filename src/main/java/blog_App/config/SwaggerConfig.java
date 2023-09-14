package blog_App.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
		name = "securityScheme",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"

)
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot Data JPA API",
				description = "Spring boot application",
				version = "v0.0.1",
				contact = @Contact(
						name = "Faheem Ali",
						email = "faheempanhwar62@gmail.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "http://springdoc.org"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "SpringShop Wiki Documentation",
				url = "https://springshop.wiki.github.org/docs"
		)
)
public class SwaggerConfig {

	//this is replacing by annotation

//	@Bean
//	public OpenAPI springShopOpenAPI() {
//		String securityScheme="bearerScheme";
//		return new OpenAPI()
//				.addSecurityItem(new SecurityRequirement()
//						.addList(securityScheme)
//				)
//				.components(new Components()
//						.addSecuritySchemes(securityScheme, new SecurityScheme()
//								.name(securityScheme)
//								.type(SecurityScheme.Type.HTTP)
//								.bearerFormat("JWT")
//								.scheme("bearer")
//						)
//				)
//				.info(new Info().title("Spring Boot Data JPA API")
//						.description("Spring boot application")
//						.version("v0.0.1")
//						.license(new License().name("Apache 2.0").url("http://springdoc.org")))
//				.externalDocs(new ExternalDocumentation()
//						.description("SpringShop Wiki Documentation")
//						.url("https://springshop.wiki.github.org/docs"));
//	}

//	public static final String AUTHORIZATION_HEADER = "Authorization";

//	private ApiKey apiKey(){
//		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
//	}

//	@Bean
//	public Docket api(){
//		return new Docket(DocumentationType.SWAGGER_2)
//				.apiInfo(getInfo())
//				.securityContexts(Arrays.asList(securityContext()))
//				.securitySchemes(Arrays.asList(apiKey()))
//				.select()
//				.apis(RequestHandlerSelectors.any())
//				.paths(PathSelectors.any())
//				.build();
//	}
//	private ApiInfo getInfo() {
//		return new ApiInfo("Spring Boot Data JPA Demo " +
//				"assigned by " +
//				"Hassan Raza (Royal Cyber Inc)",
//				"This is developed by Faheem Ali Employee of the Royal Cyber", "1.0",
//				"Terms of Service", new Contact("Faheem Ali","faheem.ali@royalcyber.com","Royal Cyber Inc."), "License of APIS", "APIS URL", Collections.emptyList());
//	}
//
//	private SecurityContext securityContext(){
//		return SecurityContext.builder().securityReferences(defaultAuth()).build();
//	}
//
//	private List<SecurityReference> defaultAuth(){
//		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//		authorizationScopes[0] = authorizationScope;
//		return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
//	}

}
