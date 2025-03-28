package com.everestuniversity.controller;

import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.everestuniversity.dto.AdmissionRequestDTO;
import com.everestuniversity.entity.AdmissionRequest;
import com.everestuniversity.service.AdmissionRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/public/admission")
@CrossOrigin(origins = "http://localhost:5173")
public class AdmissionController {

	private static final Logger logger = LoggerFactory.getLogger(AdmissionController.class);

	@Autowired
	private AdmissionRequestService admissionRequestService;

	@PostMapping(value = "/registration", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> admissionRegistration(
			@RequestPart(value = "tenthFile", required = true) MultipartFile tenthFile,
			@RequestPart(value = "twelthFile", required = true) MultipartFile twelthFile,
			@RequestPart(value = "registrationJson", required = true) String registrationJson) {

		HashMap<String, Object> response = new HashMap<>();

		try {
			// Parse registration JSON first to check for existing email
			ObjectMapper objectMapper = new ObjectMapper();
			AdmissionRequestDTO admissionRequestDTO = objectMapper.readValue(registrationJson,
					AdmissionRequestDTO.class);

			// Check if email already exists
			if (admissionRequestService.existsByEmail(admissionRequestDTO.getEmail())) {
				response.put("success", false);
				response.put("message", "Email already registered");
				return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
			}

			// Continue with file validation
			if (!isValidFileType(tenthFile) || !isValidFileType(twelthFile)) {
				response.put("success", false);
				response.put("message", "Invalid file type. Only PDF files are allowed");
				return ResponseEntity.badRequest().body(response);
			}

			// Validate file sizes (5MB limit)
			if (!isValidFileSize(tenthFile) || !isValidFileSize(twelthFile)) {
				response.put("success", false);
				response.put("message", "File size exceeds 5MB limit");
				return ResponseEntity.badRequest().body(response);
			}

			// Validate DTO
			if (!isValidAdmissionRequest(admissionRequestDTO)) {
				response.put("success", false);
				response.put("message", "Invalid registration data");
				return ResponseEntity.badRequest().body(response);
			}

			String fullName = String.format("%s %s %s",
					admissionRequestDTO.getSurName().trim(),
					admissionRequestDTO.getFirstName().trim(),
					admissionRequestDTO.getMiddleName().trim())
					.replaceAll("\\s+", " ")
					.trim();

			// Save files and get paths
			String tenthFilePath = admissionRequestService.saveFilePath(tenthFile, fullName);
			String twelthFilePath = admissionRequestService.saveFilePath(twelthFile, fullName);

			// Create and save admission request
			AdmissionRequest request = admissionRequestService.mapDtoToEntity(
					admissionRequestDTO,
					tenthFilePath,
					twelthFilePath);

			// Save registration and admission
			admissionRequestService.saveRegistration(request);
			admissionRequestService.saveAdmission(request.getRegistrationId());

			// Prepare success response
			response.put("success", true);
			response.put("message", "Registration successful");
			response.put("data", request);

			logger.info("Registration successful for student: {}", fullName);
			return ResponseEntity.ok(response);

		} catch (DataIntegrityViolationException e) {
			logger.error("Registration failed - Duplicate email: ", e);
			response.put("success", false);
			response.put("message", "This email is already registered");
			return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
		} catch (Exception e) {
			logger.error("Registration failed: ", e);
			response.put("success", false);
			response.put("message", "Registration failed: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	private boolean isValidFileType(MultipartFile file) {
		return file != null &&
				(file.getContentType() != null &&
						file.getContentType().equals("application/pdf"));
	}

	private boolean isValidFileSize(MultipartFile file) {
		return file != null && file.getSize() <= 5 * 1024 * 1024; // 5MB limit
	}

	private boolean isValidAdmissionRequest(AdmissionRequestDTO dto) {
		return dto != null &&
				dto.getSurName() != null && !dto.getSurName().trim().isEmpty() &&
				dto.getFirstName() != null && !dto.getFirstName().trim().isEmpty() &&
				dto.getEmail() != null && dto.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$") &&
				dto.getMobileNo() != null && dto.getMobileNo().matches("^\\d{10}$");
	}
}
