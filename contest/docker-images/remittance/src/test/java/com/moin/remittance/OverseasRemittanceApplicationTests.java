package com.moin.remittance;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@EnableJpaRepositories(basePackages = "com.moin.remittance.repository")
class OverseasRemittanceApplicationTests {

	@Test
	void contextLoads() {
	}

}
