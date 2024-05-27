package com.shopnest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Shop Nest Application", version = "1.0", description = "API for managing Products and checkouts"))
@EnableScheduling
public class ShopNestApplication {

	@Configuration
	class OpenApiConfig {
		@Bean
		public OpenAPI customConfig() {
			final String securitySchemeName = "Bearer Authentication";
			return new OpenAPI()
					.addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
					.components(new Components().addSecuritySchemes(securitySchemeName, new SecurityScheme()
							.name(securitySchemeName)
							.type(SecurityScheme.Type.HTTP)
							.scheme("bearer")
							.bearerFormat("JWT")));
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(ShopNestApplication.class, args);
	}

	@PostConstruct
	public void startBackgroundThread() {
		Thread thread = new Thread(() -> {
			while (true) {
				try {
					System.out.println("Background task running...");
					Thread.sleep(15000); // Sleep for 15 seconds
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					break;
				}
			}
		});
		thread.setDaemon(true); // Ensure the thread does not prevent JVM shutdown
		thread.start();
	}

	@Scheduled(fixedRate = 10000)
	public void keepAliveTask() {
		System.out.println("Running keep-alive task at " + System.currentTimeMillis());
	}

	@RestController
	class KeepAliveController {
		@GetMapping("/keep-alive")
		public String keepAlive() {
			return "Application is alive";
		}
	}
}
