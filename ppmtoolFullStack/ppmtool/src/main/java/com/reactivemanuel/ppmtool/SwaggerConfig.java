package com.reactivemanuel.ppmtool;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)
			  .useDefaultResponseMessages(false)
			  .select()                                  
			  .apis(RequestHandlerSelectors.any())
			  .paths(regex("/api.*"))  
			  .build()
			  .apiInfo(apiInfo());                                           
    }
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
                .title("PPM Tool - Swagger & Java Spring API")
                .description("Spring Boot MVC Example API reference for developers")
                .termsOfServiceUrl("http://hantsy.blogspot.com")
                .contact(new Contact("Manuel Santiago", "http://ppmtool.us-east-1.elasticbeanstalk.com/", "no email"))
                .license("Apache 2.0")
                .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
                .version("1.0.0")
                .build();
	}	
		
}