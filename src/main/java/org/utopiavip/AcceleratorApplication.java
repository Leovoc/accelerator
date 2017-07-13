package org.utopiavip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.utopiavip")
public class AcceleratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcceleratorApplication.class, args);
	}
}
