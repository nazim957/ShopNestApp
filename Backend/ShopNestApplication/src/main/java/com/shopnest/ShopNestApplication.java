package com.shopnest;

import com.shopnest.model.Product;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.shopnest.controller.ProductController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

	@Autowired
	private ProductController productController;

	@Scheduled(fixedRate = 200000)
	public void callGetAllProducts() {
		ResponseEntity<List<Product>> response = productController.getAllProducts();
		// You can add some logging or handling here if needed
		System.out.println("Called getAllProducts: " + response.getStatusCode());
	}

	@RestController
	class KeepAliveController {
		@GetMapping("/keep-alive")
		public String keepAlive() {
			return "Application is alive";
		}
	}
}
