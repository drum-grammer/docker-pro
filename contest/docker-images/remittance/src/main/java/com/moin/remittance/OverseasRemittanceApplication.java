package com.moin.remittance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.moin.remittance.repository")
@SpringBootApplication(scanBasePackages = "com.moin.remittance.*")
public class OverseasRemittanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OverseasRemittanceApplication.class, args);
	}

}
