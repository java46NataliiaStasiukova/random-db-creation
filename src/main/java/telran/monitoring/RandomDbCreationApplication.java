package telran.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PreDestroy;

@SpringBootApplication
public class RandomDbCreationApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(RandomDbCreationApplication.class, args);
	}
	
	@PreDestroy
	void preDestroy() {
		System.out.println("RandomDbCreation - shutdown has been performed");
	}

}
