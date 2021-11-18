package com.webFlux.cosmos.resilience;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class WebFluxCosmosApplication {

	public static void main(String[] args) {
	    SpringApplication.run(WebFluxCosmosApplication.class, args);
		}

}
