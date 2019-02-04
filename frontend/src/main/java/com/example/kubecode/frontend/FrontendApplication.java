package com.example.kubecode.frontend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FrontendApplication {
	@Bean
	public RestTemplate restTemplate() { return new RestTemplate(); }

	public static void main(String[] args) {
		SpringApplication.run(FrontendApplication.class, args);
	}
}

@RestController
class FrontendController {
	private final RestTemplate restTemplate;
	private final String backendServiceAddress;

	FrontendController(RestTemplate restTemplate, @Value("${BACKEND_SERVICE_ADDR:127.0.0.1}") String backendServiceAddress) {
		this.restTemplate = restTemplate;
		this.backendServiceAddress = backendServiceAddress;
	}

	@GetMapping("/")
	public String index() {
		final String frontendMessage = "Hello Node World from Frontend!";
		final String backendMessage = restTemplate.getForObject(String.format("http://%s", backendServiceAddress), String.class);

		return String.format("%s<br>%s", frontendMessage, backendMessage);
	}

}


