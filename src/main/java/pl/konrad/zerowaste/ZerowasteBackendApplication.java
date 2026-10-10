package pl.konrad.zerowaste;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ZerowasteBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZerowasteBackendApplication.class, args);
	}

}

