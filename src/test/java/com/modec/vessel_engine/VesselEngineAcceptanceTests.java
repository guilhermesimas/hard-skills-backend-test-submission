package com.modec.vessel_engine;

import com.fasterxml.jackson.databind.JsonNode;
import com.modec.vessel_engine.entities.Equipment;
import com.modec.vessel_engine.contracts.HttpError;
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
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Transactional
class VesselEngineAcceptanceTests {

	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	void testWhenCreatingVessel_withoutCode_badRequest() throws IOException {
		ResponseEntity<String> response = post("/vessels", "invalid-body", String.class);
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

	@Test
	void testWhenRegisteringEquipment_withExistingVesselAndInvalidBody_badRequest() throws IOException {
		ResponseEntity<HttpError> response = post("/vessels/MV123/equipment", "register-equipment-no-code", HttpError.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		Assertions.assertThat(response.getBody()).hasFieldOrProperty("errorMessage");
	}

	@Test
	void testWhenRegisteringEquipment_withExistingVesselAndRepeatedCode_conflict() throws IOException {
		ResponseEntity<HttpError> response = post("/vessels/MV123/equipment", "register-equipment-repeated", HttpError.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
		Assertions.assertThat(response.getBody()).hasFieldOrProperty("errorMessage");
	}

	@Test
	void testWhenDeactivatingEquipment_withExistingEquipment_noContent() throws IOException {
		ResponseEntity<Void> response = post("/vessels/MV123/equipment/deactivate", "deactivate-equipment", Void.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}

	@Test
	void testWhenDeactivatingEquipment_withNonExistentEquipment_unprocessableEntity() throws IOException {
		ResponseEntity<HttpError> response = post("/vessels/MV123/equipment/deactivate", "deactivate-equipment-too-many", HttpError.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@Test
	void testWhenDeactivatingEquipment_withNoEquipmentList_badRequest() throws IOException {
		ResponseEntity<HttpError> response = post("/vessels/MV123/equipment/deactivate", "invalid-body", HttpError.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	void testWhenListingActiveEquipment_withNonExistentVessel_notFound() throws IOException {
		ResponseEntity<HttpError> response = get("/vessels/MV404/equipment", HttpError.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void testWhenListingActiveEquipment_ok() throws IOException {
		ResponseEntity<List> response = get("/vessels/MV456/equipment", List.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		List<LinkedHashMap> responseBody = response.getBody();
		Assertions.assertThat(responseBody).hasSize(1);
		Assertions.assertThat(responseBody.get(0).get("name")).isEqualTo("shouldappear");
	}

	protected  <T> ResponseEntity<T> post(String url, String fileName, Class<T> responseType) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(Json.loadJson(fileName), headers);
		return testRestTemplate.postForEntity(url, request, responseType);
	}

	protected  <T> ResponseEntity<T> get(String url, Class<T> responseType) throws IOException {
		return testRestTemplate.getForEntity(url, responseType);
	}

}
