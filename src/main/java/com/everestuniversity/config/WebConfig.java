package com.everestuniversity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // Apply CORS globally or restrict as needed
				.allowedOrigins("http://localhost:5173/") // Allow only this origin
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Include OPTIONS
				.allowedHeaders("*") // Allow all headers if needed
				.allowCredentials(true); // Needed if sending cookies or auth headers
	}
}