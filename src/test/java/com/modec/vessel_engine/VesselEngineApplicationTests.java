package com.modec.vessel_engine;

import com.modec.vessel_engine.entities.Equipment;
import com.modec.vessel_engine.entities.HttpError;
import com.modec.vessel_engine.entities.Vessel;
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
	void testWhenCreatingVessel_withValidBodyAndUniqueCode_created() throws IOException {
		ResponseEntity<Vessel> response = post("/vessels", "create-vessel", Vessel.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		Assertions.assertThat(response.getBody()).hasFieldOrProperty("code");
	}

	@Test
	void testWhenCreatingVessel_withValidBodyAndRepeatedCode_conflict() throws IOException {
		ResponseEntity<HttpError> response = post("/vessels", "create-vessel-repeated", HttpError.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
		Assertions.assertThat(response.getBody()).hasFieldOrProperty("errorMessage");
	}

	@Test
	void testWhenRegisteringEquipment_withNonExistingVessel_notFound() throws IOException {
		ResponseEntity<HttpError> response = post("/vessels/MV404/equipment", "register-equipment", HttpError.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		Assertions.assertThat(response.getBody()).hasFieldOrProperty("errorMessage");
	}

	@Test
	void testWhenRegisteringEquipment_withExistingVesselAndValidBody_created() throws IOException {
		ResponseEntity<Equipment> response = post("/vessels/MV123/equipment", "register-equipment", Equipment.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

	protected  <T> ResponseEntity<T> post(String url, String fileName, Class<T> responseType) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(Json.loadJson(fileName), headers);
		return testRestTemplate.postForEntity(url, request, responseType);
	}

}
