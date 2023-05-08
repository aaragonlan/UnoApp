package es.proyecto.uno.UnoApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UnoAppApplication {

	public static void main(String[] args) {
		System.out.println("== START ==");
		SpringApplication.run(UnoAppApplication.class, args);
		System.out.println("=== START ===");
	}

}
