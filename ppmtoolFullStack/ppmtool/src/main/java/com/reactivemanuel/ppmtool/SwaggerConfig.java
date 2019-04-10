package com.reactivemanuel.ppmtool;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.google.common.collect.Lists;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
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
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/*
 * Security: https://stackoverflow.com/questions/50545286/spring-boot-swagger-ui-set-jwt-token
 * 
 */

@EnableSwagger2
@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
	
	public static final String AUTHORIZATION_HEADER 	= "Authorization";
    public static final String DEFAULT_INCLUDE_PATTERN 	= "/api/.*";
    public static final String JWT_PATH_01 				= "/api/project.*";
    public static final String JWT_PATH_02 				= "/api/backlog.*";
    private final Logger log = LoggerFactory.getLogger(SwaggerConfig.class);
	
//	@Bean
//    public Docket api() { 
//        return new Docket(DocumentationType.SWAGGER_2)
////			  .useDefaultResponseMessages(false)
//			  .select()                                  
////			  .apis(RequestHandlerSelectors.any())
//			  .apis(RequestHandlerSelectors.basePackage("com.reactivemanuel.ppmtool.web"))
//			  .paths(regex(DEFAULT_INCLUDE_PATTERN))  
////			  .paths(PathSelectors.ant("/api/*"))  
////			  .paths(PathSelectors.any())   
//			  .build()
//			  .apiInfo(apiInfo());                                           
//    }

	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)
			  .useDefaultResponseMessages(false)
			  .forCodeGeneration(true)
			  .securityContexts(Lists.newArrayList(securityContext()))
			  .securitySchemes(Lists.newArrayList(apiKey()))
			  .genericModelSubstitutes(ResponseEntity.class)
			  .ignoredParameterTypes(Pageable.class)
			  .ignoredParameterTypes(java.sql.Date.class)
			  .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
			  .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
			  .directModelSubstitute(java.time.LocalDateTime.class, Date.class)			  
			  .select()                                  
			  .apis(RequestHandlerSelectors.basePackage("com.reactivemanuel.ppmtool.web"))
			  .paths(regex(DEFAULT_INCLUDE_PATTERN))  
			  .build()
			  .apiInfo(apiInfo());                                           
    }
    
    
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
                .title("PPM Tool - Swagger & Java Spring API")
                .description("Spring Boot MVC Example API reference for developers")
                .termsOfServiceUrl("http://ppmtool.us-east-1.elasticbeanstalk.com/")
                .contact(new Contact("Manuel Santiago", "http://ppmtool.us-east-1.elasticbeanstalk.com/", "email@email.com"))
                .license("Apache 2.0")
                .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
                .version("1.0.0")
                .build();
	}	
	
	private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }
	
	private SecurityContext securityContext() {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
//            .forPaths(PathSelectors.regex(JWT_PATH_01))
//            .forPaths(PathSelectors.regex(JWT_PATH_02))
            .build();
    }
	
	List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
            = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("JWT", authorizationScopes));
    }
}