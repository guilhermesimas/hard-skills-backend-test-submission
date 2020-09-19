package com.modec.vessel_engine;

import com.modec.vessel_engine.contracts.VesselTO;
import com.modec.vessel_engine.utils.Json;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class VesselEngineApplicationTests {

	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	void testWhenCreatingVessel_withoutCode_badRequest() throws IOException {
		ResponseEntity<String> response = post("/vessels", "create-vessel-no-code", String.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	void testWhenCreatingVessel_withValidBodyAndUniqueCode_ok() throws IOException {
		ResponseEntity<VesselTO> response = post("/vessels", "create-vessel", VesselTO.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(response.getBody()).hasFieldOrProperty("code");
	}

	protected  <T> ResponseEntity<T> post(String url, String fileName, Class<T> responseType) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(Json.loadJson(fileName), headers);
		return testRestTemplate.postForEntity(url, request, responseType);
	}

}
