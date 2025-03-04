package com.everestuniversity.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class CloudinaryConfig {

	private final Dotenv dotenv;

	public CloudinaryConfig() {
		// Load the .env file from the specified path
		this.dotenv = Dotenv.configure().directory("D://Everest University").filename(".env").load();
	}

	@Bean
	public Cloudinary cloudinary() {
		Map<String, String> config = new HashMap<>();
		config.put("cloud_name", dotenv.get("CLOUDINARY_CLOUD_NAME"));
		config.put("api_key", dotenv.get("CLOUDINARY_API_KEY"));
		config.put("api_secret", dotenv.get("CLOUDINARY_API_SECRET"));
		return new Cloudinary(config);
	}
}
