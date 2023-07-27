package kr.dnt.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DntApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DntApiApplication.class, args);
	}

}
