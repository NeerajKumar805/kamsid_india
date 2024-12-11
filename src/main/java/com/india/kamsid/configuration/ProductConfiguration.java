package com.india.kamsid.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Author: Neeraj Kumar
 * Date: 2024-11-25
 */

@Configuration
public class ProductConfiguration implements WebMvcConfigurer {
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Exposing the 'E://products-images/' directory as '/products-images/' in the URL
        registry.addResourceHandler("/products-images/**")
                .addResourceLocations("file:///E:/products-images/");
    }
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping("/**")
	        .allowedOrigins("http://localhost:5173")  // Replace with your frontend URL
	        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Specify methods if needed
	        .allowedHeaders("*")  // Allow all headers
	        .allowCredentials(true);  // Allow credentials if needed
	}

	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
