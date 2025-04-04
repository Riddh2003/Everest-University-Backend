package com.everestuniversity.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.everestuniversity.dto.StudentDto;
import com.everestuniversity.entity.AdminEntity;
import com.everestuniversity.entity.AdmissionRequest;
import com.everestuniversity.repository.AdminRepository;
import com.everestuniversity.repository.AdmissionRequestRepository;
import com.everestuniversity.service.AdmissionRequestService;
import com.everestuniversity.service.AdmissionService;
import com.everestuniversity.service.MailService;
import com.everestuniversity.service.UUIDService;

@RestController
@RequestMapping("/api/private/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

	@Autowired
	private AdminRepository adminRepo;

	@Autowired
	private AdmissionRequestService admissionRequestService;

	@Autowired
	private AdmissionService admissionService;

	@Autowired
	private AdmissionRequestRepository admissionRequestRepo;

	@Autowired
	private MailService mailService;

	@GetMapping("/getalladmin")
	public ResponseEntity<?> getAllAdmin() {
		HashMap<String, Object> response = new HashMap<>();
		response.put("success", true);
		response.put("message", "Admin fetched successfully");
		response.put("data", adminRepo.findAll());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/getadminbyid")
	public ResponseEntity<?> getUserById(@RequestParam UUID userId) {
		HashMap<String, Object> response = new HashMap<>();
		AdminEntity admin = adminRepo.findById(userId).orElse(null);
		if (admin != null) {
			response.put("success", true);
			response.put("message", "Admin fetched successfully");
			response.put("data", admin);
			return ResponseEntity.ok(response);
		}
		response.put("success", false);
		response.put("message", "Admin not found");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	@PostMapping("/approve")
    public ResponseEntity<?> approveAdmission(@RequestBody StudentDto studentDto) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            String registrationId = studentDto.getRegistrationId();

            if (registrationId == null || registrationId.isEmpty()) {
                response.put("message", "Registration ID is required");
                response.put("success", false);
                return ResponseEntity.badRequest().body(response);
            }

            // Remove "0x" prefix if present
            String sanitizedId = registrationId.startsWith("0x") ? registrationId.substring(2) : registrationId;
            UUID uuid = UUIDService.formatUuid(sanitizedId);

            Optional<AdmissionRequest> op = admissionRequestRepo.findById(uuid);
            if (!op.isPresent()) {
                response.put("message", "Registration not found");
                response.put("success", false);
                return ResponseEntity.badRequest().body(response);
            }

            AdmissionRequest registration = op.get();
            
            // Approve the admission
            admissionRequestService.approveRegistration(uuid);
            admissionService.approveAdmission(uuid);

            // Send enrollment email with credentials
            mailService.sendEnrollementAndPassword(registration.getEmail());

            // Delete the admission request after approval
            admissionRequestRepo.delete(registration);

            response.put("message", "Admission approved successfully");
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Failed to approve admission");
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/reject")
    public ResponseEntity<?> rejectAdmission(@RequestBody Map<String, String> payload) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            String registrationId = payload.get("registrationId");

            if (registrationId == null || registrationId.isEmpty()) {
                response.put("message", "Registration ID is required");
                response.put("success", false);
                return ResponseEntity.badRequest().body(response);
            }

            String sanitizedId = registrationId.startsWith("0x") ? registrationId.substring(2) : registrationId;
            UUID uuid = UUIDService.formatUuid(sanitizedId);

            Optional<AdmissionRequest> op = admissionRequestRepo.findById(uuid);
            if (!op.isPresent()) {
                response.put("message", "Registration not found");
                response.put("success", false);
                return ResponseEntity.badRequest().body(response);
            }

            AdmissionRequest registration = op.get();

            // Reject admission logic
            admissionRequestService.rejectRegistration(uuid);

            response.put("message", "Admission rejected successfully");
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Failed to reject admission");
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
	@GetMapping("/getalladmissionsrequest")
	public ResponseEntity<?> getAllAdmissionsRequest() {
		HashMap<String, Object> response = new HashMap<>();
		try {
			List<AdmissionRequest> admissions = admissionRequestRepo.findAll();

			if (admissions.isEmpty()) {
				response.put("success", false);
				response.put("message", "No admission requests found");
			} else {
				response.put("success", true);
				response.put("message", "Admission requests fetched successfully");
				response.put("data", admissions);
			}

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Failed to fetch admission requests");
			response.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

}
