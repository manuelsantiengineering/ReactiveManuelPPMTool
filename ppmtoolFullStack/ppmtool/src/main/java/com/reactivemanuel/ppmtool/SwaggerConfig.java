package com.reactivemanuel.ppmtool;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Date;
import java.util.List;

import com.fasterxml.classmate.TypeResolver;
//import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
//import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.reactivemanuel.ppmtool.web.BacklogController;
import com.reactivemanuel.ppmtool.web.ProjectController;
import com.reactivemanuel.ppmtool.web.UserController;


import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
//import springfox.documentation.swagger.web.ApiKeyVehicle;
//import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;


/*
 * Security: https://stackoverflow.com/questions/50545286/spring-boot-swagger-ui-set-jwt-token
 * 
 */

@EnableSwagger2
@Configuration
@Import(BeanValidatorPluginsConfiguration.class)

@ComponentScan(basePackageClasses = {
	    BacklogController.class,
	    ProjectController.class,
	    UserController.class
	})

public class SwaggerConfig {
	
	public static final String AUTHORIZATION_HEADER 	= "Authorization";
    public static final String DEFAULT_INCLUDE_PATTERN 	= "/api/.*";

    @Autowired
    private TypeResolver typeResolver;
        
//    @Bean
//    public Docket api() {
//      return new Docket(DocumentationType.SWAGGER_2)
//          .select()
//          .apis(RequestHandlerSelectors.basePackage("com.reactivemanuel.ppmtool.web"))
//          .paths(regex(DEFAULT_INCLUDE_PATTERN))
//          .build()
//          .pathMapping("/")
//          .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
//		  .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
//		  .directModelSubstitute(java.time.LocalDateTime.class, Date.class)	
//          .genericModelSubstitutes(ResponseEntity.class)
//          .useDefaultResponseMessages(false)
//		  .globalResponseMessage(RequestMethod.GET,defaultGetResponses())
//		  .globalResponseMessage(RequestMethod.POST,defaultPostResponses())
//		  .globalResponseMessage(RequestMethod.DELETE,defaultGetResponses())
//		  .globalResponseMessage(RequestMethod.PATCH,defaultGetResponses())
//          .securitySchemes(Lists.newArrayList(apiKey()))
//          .securityContexts(Lists.newArrayList(securityContext()))
//          .enableUrlTemplating(true)
//          .apiInfo(apiInfo()); 
//    }
    
    
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)
        	  .select()  
        	  .apis(RequestHandlerSelectors.basePackage("com.reactivemanuel.ppmtool.web"))
        	  .paths(regex(DEFAULT_INCLUDE_PATTERN))  
        	  .build()        	  
              .pathMapping("/")
              .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
			  .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
			  .directModelSubstitute(java.time.LocalDateTime.class, Date.class)	
			  .genericModelSubstitutes(ResponseEntity.class)		  
			  .useDefaultResponseMessages(false)
			  .globalResponseMessage(RequestMethod.GET,defaultGetResponses())
			  .globalResponseMessage(RequestMethod.POST,defaultPostResponses())
			  .globalResponseMessage(RequestMethod.DELETE,defaultGetResponses())
			  .globalResponseMessage(RequestMethod.PATCH,defaultGetResponses())
			  .securitySchemes(Lists.newArrayList(apiKey()))
			  .securityContexts(Lists.newArrayList(securityContext()))
			  
//			  .forCodeGeneration(true)			  			 
//			  .ignoredParameterTypes(Pageable.class)
//			  .ignoredParameterTypes(java.sql.Date.class)

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
            .forPaths(securityPaths())
//            .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))                 
            .build();
    }
	
	List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
            = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("JWT", authorizationScopes));
    }
	
	@Bean
	  UiConfiguration uiConfig() {
	    return UiConfigurationBuilder.builder()
	        .deepLinking(true)
	        .displayOperationId(false)
	        .defaultModelsExpandDepth(1)
	        .defaultModelExpandDepth(1)
	        .defaultModelRendering(ModelRendering.EXAMPLE)
	        .displayRequestDuration(false)
	        .docExpansion(DocExpansion.NONE)
	        .filter(false)
	        .maxDisplayedTags(null)
	        .operationsSorter(OperationsSorter.ALPHA)
	        .showExtensions(false)
	        .tagsSorter(TagsSorter.ALPHA)
	        .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
	        .validatorUrl(null)
	        .build();
	  }
	
	private Predicate<String> securityPaths() {
	    return or(
	        regex("/api/project/.*"),
	        regex("/api/backlog/.*")
	        );
	  }
	
	List<ResponseMessage> defaultGetResponses(){
		return Lists.newArrayList(
				new ResponseMessageBuilder().code(200).message("Success.").build(),
				new ResponseMessageBuilder().code(401).message("Unauthorized.").build(),
				new ResponseMessageBuilder().code(500).message("Unexpected error.").build()
            		);
	}
	
	List<ResponseMessage> defaultPostResponses(){
		return Lists.newArrayList(
				new ResponseMessageBuilder().code(200).message("Success.").build(),
				new ResponseMessageBuilder().code(500).message("Unexpected error.").build()
            		);
	}
}