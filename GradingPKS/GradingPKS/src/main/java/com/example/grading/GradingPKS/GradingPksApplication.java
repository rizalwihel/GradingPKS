package com.example.grading.GradingPKS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.example.grading.GradingPKS.model")
@EnableJpaRepositories("com.example.grading.GradingPKS.repository")
public class GradingPksApplication {

	public static void main(String[] args) {
		// Mengambil port dari environment variable untuk cloud hosting
		String port = System.getenv("PORT");
		if (port != null) {
			System.setProperty("server.port", port);
		}
		
		// Set profile ke prod jika tidak ada yang diset
		if (System.getProperty("spring.profiles.active") == null) {
			System.setProperty("spring.profiles.active", "prod");
		}
		
		SpringApplication.run(GradingPksApplication.class, args);
	}

}
