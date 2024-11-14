package edu.eci.cvds.BiblioSoftLoans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "edu.eci.cvds.BiblioSoftLoans.repository")
public class BiblioSoftLoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiblioSoftLoansApplication.class, args);
	}
}
