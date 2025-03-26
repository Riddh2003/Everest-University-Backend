package com.everestuniversity.config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import com.cloudinary.Cloudinary;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class CloudinaryConfig {

	private final Dotenv dotenv;

	public CloudinaryConfig() {
		Dotenv tempDotenv = null;
		try {
			// Try to load from multiple possible locations
			File currentDir = new File(".");
			System.out.println("Current directory: " + currentDir.getAbsolutePath());

			// First try loading from the project root
			if (new File(".env").exists()) {
				tempDotenv = Dotenv.configure().load();
				System.out.println("Loaded .env from current directory");
			}
			// Then try loading from classpath
			else if (ResourceUtils.getFile("classpath:.env").exists()) {
				tempDotenv = Dotenv.configure().filename(".env").load();
				System.out.println("Loaded .env from classpath");
			}
			// Then try with specific path
			else if (new File("Everest University/.env").exists()) {
				tempDotenv = Dotenv.configure().directory("Everest University").load();
				System.out.println("Loaded .env from Everest University directory");
			}
			// Finally try with specific absolute path as a last resort
			else {
				// Use proper file separators for platform compatibility
				String directory = System.getProperty("user.dir");
				tempDotenv = Dotenv.configure().directory(directory).load();
				System.out.println("Loaded .env from: " + directory);
			}
		} catch (Exception e) {
			System.err.println("Failed to load .env file: " + e.getMessage());
			// Use default values as fallback
			tempDotenv = null;
		}
		this.dotenv = tempDotenv;
	}

	@Bean
	public Cloudinary cloudinary() {
		Map<String, String> config = new HashMap<>();

		// Use environment variables or default values if dotenv failed
		if (dotenv != null) {
			config.put("cloud_name", dotenv.get("CLOUDINARY_CLOUD_NAME"));
			config.put("api_key", dotenv.get("CLOUDINARY_API_KEY"));
			config.put("api_secret", dotenv.get("CLOUDINARY_API_SECRET"));
		} else {
			// Fallback to environment variables
			config.put("cloud_name",
					System.getenv("CLOUDINARY_CLOUD_NAME") != null ? System.getenv("CLOUDINARY_CLOUD_NAME")
							: "default_cloud_name");
			config.put("api_key", System.getenv("CLOUDINARY_API_KEY") != null ? System.getenv("CLOUDINARY_API_KEY")
					: "default_api_key");
			config.put("api_secret",
					System.getenv("CLOUDINARY_API_SECRET") != null ? System.getenv("CLOUDINARY_API_SECRET")
							: "default_api_secret");
		}

		return new Cloudinary(config);
	}
}
