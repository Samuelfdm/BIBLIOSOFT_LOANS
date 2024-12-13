package edu.eci.cvds.BiblioSoftLoans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BiblioSoftLoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiblioSoftLoansApplication.class, args);
	}
}