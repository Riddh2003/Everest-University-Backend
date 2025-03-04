package com.everestuniversity.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.everestuniversity.dto.AdmissionRequestDTO;
import com.everestuniversity.entity.AdmissionRequest;
import com.everestuniversity.service.AdmissionRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/public/admission/")
public class AdmissionController {

	@Autowired
	private AdmissionRequestService admissionRequestService;

	@PostMapping("/registration")
	public ResponseEntity<?> admissionRegistration(@RequestPart("tenthFile") MultipartFile tenthFile,
			@RequestPart("twelthFile") MultipartFile twelthFile,
			@RequestPart("registrationJson") String registrationJson) {
		HashMap<String, Object> response = new HashMap<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			AdmissionRequestDTO admissionRequestDTO = objectMapper.readValue(registrationJson,
					AdmissionRequestDTO.class);

			String fullName = admissionRequestDTO.getSurName() + " " + admissionRequestDTO.getFirstName() + " "
					+ admissionRequestDTO.getMiddleName();

			String tenthFilePath = admissionRequestService.saveFilePath(tenthFile, fullName);
			String twelthFilePath = admissionRequestService.saveFilePath(twelthFile, fullName);

			AdmissionRequest request = admissionRequestService.mapDtoToEntity(admissionRequestDTO, tenthFilePath,
					twelthFilePath);
			admissionRequestService.saveRegistration(request);
			admissionRequestService.saveAdmission(request.getRegistrationId());
			response.put("message", "Registration successful");
			response.put("success", true);
			response.put("data", request);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("message", "Registration failed");
			response.put("success", false);
			response.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}

}
