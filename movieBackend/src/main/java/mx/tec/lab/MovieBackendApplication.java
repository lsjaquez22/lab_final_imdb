package mx.tec.lab;

import mx.tec.lab.service.SessionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieBackendApplication {

	public static void main(String[] args) {
		SessionHandler.getInstance();
		SpringApplication.run(MovieBackendApplication.class, args);
	}

}
