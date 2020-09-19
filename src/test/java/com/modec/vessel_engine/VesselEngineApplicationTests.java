package com.modec.vessel_engine;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class VesselEngineApplicationTests {

	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	void testWhenCreatingVessel_ok() {
		ResponseEntity<String> response = testRestTemplate.postForEntity("/vessels", null, String.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}
