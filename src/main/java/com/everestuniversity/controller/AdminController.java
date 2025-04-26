package com.everestuniversity.controller;

import java.beans.Encoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.everestuniversity.dto.FacultyDto;
import com.everestuniversity.dto.StudentDto;
import com.everestuniversity.entity.AdminEntity;
import com.everestuniversity.entity.AdmissionRequest;
import com.everestuniversity.entity.FacultyEntity;
import com.everestuniversity.entity.StudentEntity;
import com.everestuniversity.repository.AdminRepository;
import com.everestuniversity.repository.AdmissionRequestRepository;
import com.everestuniversity.repository.FacultyRepository;
import com.everestuniversity.repository.StudentRepository;
import com.everestuniversity.service.AdmissionRequestService;
import com.everestuniversity.service.AdmissionService;
import com.everestuniversity.service.CloudinaryService;
import com.everestuniversity.service.UUIDService;

@RestController
@RequestMapping("/api/private/admin")
@CrossOrigin(origins = { "http://localhost:5173" }, allowedHeaders = "*", allowCredentials = "true")
public class AdminController {

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private AdmissionRequestService admissionRequestService;

    @Autowired
    private AdmissionService admissionService;

    @Autowired
    private AdmissionRequestRepository admissionRequestRepo;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/getalladmin")
    public ResponseEntity<?> getAllAdmin() {
        HashMap<String, Object> response = new HashMap<>();
        try {
            List<AdminEntity> admins = adminRepo.findAll();
            response.put("success", true);
            response.put("message", "Admin fetched successfully");
            response.put("data", admins);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to fetch admin data");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/getadminbyemail")
    public ResponseEntity<?> getUserById(@RequestParam String email) {
        HashMap<String, Object> response = new HashMap<>();
        System.out.println(email);
        try {
            if (email == null || email.isEmpty()) {
                response.put("success", false);
                response.put("message", "Email is required");
                return ResponseEntity.badRequest().body(response);
            }

            Optional<AdminEntity> op = adminRepo.findByEmail(email);
            System.out.println(op.get());
            if (op.isPresent()) {
                response.put("success", true);
                response.put("message", "Admin fetched successfully");
                response.put("data", op.get());
                System.out.println(response);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Admin not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to fetch admin data");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/approve")
    public ResponseEntity<?> approveAdmission(@RequestBody StudentDto studentDto) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            if (studentDto == null) {
                response.put("message", "Student data is required");
                response.put("success", false);
                return ResponseEntity.badRequest().body(response);
            }

            String registrationId = studentDto.getRegistrationId();
            System.out.println("Received registration ID: " + registrationId);

            if (registrationId == null || registrationId.isEmpty()) {
                response.put("message", "Registration ID is required");
                response.put("success", false);
                return ResponseEntity.badRequest().body(response);
            }

            // Validate UUID format
            try {
                String sanitizedId = registrationId.startsWith("0x") ? registrationId.substring(2) : registrationId;
                UUID uuid = UUIDService.formatUuid(sanitizedId);

                Optional<AdmissionRequest> op = admissionRequestRepo.findById(uuid);
                if (!op.isPresent()) {
                    response.put("message", "Registration not found for ID: " + registrationId);
                    response.put("success", false);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }

                AdmissionRequest registration = op.get();

                Optional<StudentEntity> optional = studentRepository.findByEmail(registration.getEmail());
                // Check if student already exists with this email
                if (optional.isPresent()) {
                    response.put("message", "Student with this email already exists");
                    response.put("success", false);
                    return ResponseEntity.badRequest().body(response);
                }

                // Process approval steps
                try {
                    admissionRequestService.approveRegistration(uuid);
                    admissionService.approveAdmission(uuid);
                    admissionRequestRepo.delete(registration);
                } catch (Exception processError) {
                    throw new RuntimeException("Error during approval process: " + processError.getMessage());
                }

                response.put("message", "Admission approved successfully");
                response.put("success", true);
                return ResponseEntity.ok(response);

            } catch (IllegalArgumentException e) {
                response.put("message", "Invalid registration ID format");
                response.put("success", false);
                response.put("error", e.getMessage());
                return ResponseEntity.badRequest().body(response);
            }

        } catch (Exception e) {
            response.put("message", "Failed to approve admission");
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/reject")
    public ResponseEntity<?> rejectAdmission(@RequestBody Map<String, String> payload) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            if (payload == null || !payload.containsKey("registrationId")) {
                response.put("message", "Registration ID is required");
                response.put("success", false);
                return ResponseEntity.badRequest().body(response);
            }

            String registrationId = payload.get("registrationId");

            if (registrationId == null || registrationId.isEmpty()) {
                response.put("message", "Registration ID is required");
                response.put("success", false);
                return ResponseEntity.badRequest().body(response);
            }

            try {
                String sanitizedId = registrationId.startsWith("0x") ? registrationId.substring(2) : registrationId;
                UUID uuid = UUIDService.formatUuid(sanitizedId);

                Optional<AdmissionRequest> op = admissionRequestRepo.findById(uuid);
                if (!op.isPresent()) {
                    response.put("message", "Registration not found");
                    response.put("success", false);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }

                AdmissionRequest registration = op.get();

                // Reject admission logic
                admissionRequestService.rejectRegistration(uuid);

                response.put("message", "Admission rejected successfully");
                response.put("success", true);
                return ResponseEntity.ok(response);
            } catch (IllegalArgumentException e) {
                response.put("message", "Invalid registration ID format");
                response.put("success", false);
                response.put("error", e.getMessage());
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("message", "Failed to reject admission");
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/getalladmissionsrequest")
    public ResponseEntity<?> getAllAdmissionsRequest() {
        HashMap<String, Object> response = new HashMap<>();
        try {
            List<AdmissionRequest> admissions = admissionRequestRepo.findAll();

            response.put("success", true);
            response.put("message",
                    admissions.isEmpty() ? "No admission requests found" : "Admission requests fetched successfully");
            response.put("data", admissions);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to fetch admission requests");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
