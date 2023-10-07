package scc.vigilancia.comunitaria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import scc.vigilancia.comunitaria.services.S3Service;

@SpringBootApplication
public class Application {

	private static S3Service service;

	public Application(S3Service service) {
		Application.service = service;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		service.listS3Buckets();
	}

}
