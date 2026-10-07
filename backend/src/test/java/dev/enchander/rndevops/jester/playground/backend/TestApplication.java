package dev.enchander.rndevops.jester.playground.backend;

import org.springframework.boot.SpringApplication;

public class TestApplication {

	public static void main(String[] args) {
		new SpringApplication(BackendApplication.class, TestcontainersConfiguration.class).run(args);
	}

}
