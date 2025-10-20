package org.serratec.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info().title("Serratec Music API").version("1.0.0")
				.description("API para gerenciar usuários, artistas, músicas e playlists")
				.contact(new Contact().name("Serratec Dev").email("dev@serratec.com")));
	}
}
