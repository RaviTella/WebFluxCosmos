package com.webFlux.cosmos.resilience;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebFluxCosmosApplication {

	public static void main(String[] args) {
		System.setProperty("COSMOS.QUERYPLAN_CACHING_ENABLED", "true");
		//System.setProperty("reactor.schedulers.defaultPoolSize", "2");
		System.out.println("DEFAULT POOL SIZE " + System.getProperty("reactor.schedulers.defaultPoolSize"));
		SpringApplication.run(WebFluxCosmosApplication.class, args);

		}

}
