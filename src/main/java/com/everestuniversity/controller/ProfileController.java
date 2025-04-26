package com.everestuniversity.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.everestuniversity.dto.AdminDto;
import com.everestuniversity.dto.FacultyDto;
import com.everestuniversity.entity.AdminEntity;
import com.everestuniversity.entity.FacultyEntity;
import com.everestuniversity.repository.AdminRepository;
import com.everestuniversity.repository.FacultyRepository;
import com.everestuniversity.repository.StudentRepository;
import com.everestuniversity.service.CloudinaryService;
import com.everestuniversity.service.JwtService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/private/profile/")
public class ProfileController {

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	AdminRepository adminRepository;

	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	FacultyRepository facultyRepository;

	@Autowired
	CloudinaryService cloudinaryService;

	@Autowired
	JwtService jwtService;

	@GetMapping("/getallfaculty")
	public ResponseEntity<?> getAllFaculty() {
		List<FacultyEntity> facultyEntity = facultyRepository.findAll();
		if (facultyEntity.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No faculty found...");
		}
		return ResponseEntity.ok(facultyEntity);
	}

	@PostMapping("/addfaculty")
	public ResponseEntity<?> addFaculty(@RequestBody FacultyDto facultyDto) {
		try {
			// Check if faculty with the same email already exists
			if (facultyRepository.findByEmail(facultyDto.getEmail()).isPresent()) {
				Map<String, Object> response = new HashMap<>();
				response.put("success", false);
				response.put("message", "Faculty with this email already exists");
				return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
			}

			// Create a new faculty entity
			FacultyEntity facultyEntity = new FacultyEntity();
			facultyEntity.setName(facultyDto.getName());
			facultyEntity.setEmail(facultyDto.getEmail());
			facultyEntity.setPassword(encoder.encode(facultyDto.getPassword()));
			facultyEntity.setGender(facultyDto.getGender());
			facultyEntity.setPhoneNumber(facultyDto.getPhoneNumber());
			facultyEntity.setAddress(facultyDto.getAddress());
			facultyEntity.setRole(facultyDto.getRole());
			facultyEntity.setQualification(facultyDto.getQualification());
			facultyEntity.setStatus(facultyDto.getStatus());
			facultyEntity.setDepartment(facultyDto.getDepartment());

			// Set profile picture to null initially
			facultyEntity.setProfilePicture(null);

			System.out.println("Saved...");
			// Save the faculty entity
			facultyRepository.save(facultyEntity);

			// Create response
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Faculty added successfully");
			response.put("facultyId", facultyEntity.getFacultyId());

			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (Exception e) {
			Map<String, Object> response = new HashMap<>();
			response.put("success", false);
			response.put("message", "Error adding faculty: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PutMapping("/updatefaculty")
	public ResponseEntity<?> updateFaculty(@RequestBody FacultyDto facultyDto) {
		try {
			// Find faculty by ID
			Optional<FacultyEntity> optional = facultyRepository.findById(facultyDto.getFacultyId());
			if (optional.isEmpty()) {
				return ResponseEntity.badRequest().body("Faculty not found with the provided ID.");
			}

			FacultyEntity facultyEntity = optional.get();

			// Update faculty details
			if (facultyDto.getName() != null)
				facultyEntity.setName(facultyDto.getName());
			if (facultyDto.getEmail() != null)
				facultyEntity.setEmail(facultyDto.getEmail());
			if (facultyDto.getPhoneNumber() != null)
				facultyEntity.setPhoneNumber(facultyDto.getPhoneNumber());
			if (facultyDto.getQualification() != null)
				facultyEntity.setQualification(facultyDto.getQualification());
			if (facultyDto.getStatus() != null)
				facultyEntity.setStatus(facultyDto.getStatus());
			if (facultyDto.getPassword() != null)
				facultyEntity.setPassword(encoder.encode(facultyDto.getPassword()));
			if (facultyDto.getRole() != null)
				facultyEntity.setRole(facultyDto.getRole());
			if (facultyDto.getDepartment() != null)
				facultyEntity.setDepartment(facultyDto.getDepartment());
			if (facultyDto.getAddress() != null)
				facultyEntity.setAddress(facultyDto.getAddress());
			if (facultyDto.getGender() != null)
				facultyEntity.setGender(facultyDto.getGender());

			// Save the updated faculty details to the database
			facultyRepository.save(facultyEntity);

			return ResponseEntity.ok("Faculty profile updated successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Error updating faculty profile: " + e.getMessage());
		}
	}

	@PutMapping(value = "/updatefaculty-with-profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> updateFacultyWithProfile(@RequestPart("faculty") FacultyDto facultyDto,
			@RequestPart(value = "profilePicture", required = false) MultipartFile profilePicture) {
		try {
			// Find faculty by ID
			Optional<FacultyEntity> optional = facultyRepository.findById(facultyDto.getFacultyId());
			if (optional.isEmpty()) {
				return ResponseEntity.badRequest().body("Faculty not found with the provided ID.");
			}

			FacultyEntity facultyEntity = optional.get();

			// Update faculty details
			if (facultyDto.getName() != null)
				facultyEntity.setName(facultyDto.getName());
			if (facultyDto.getEmail() != null)
				facultyEntity.setEmail(facultyDto.getEmail());
			if (facultyDto.getPhoneNumber() != null)
				facultyEntity.setPhoneNumber(facultyDto.getPhoneNumber());
			if (facultyDto.getQualification() != null)
				facultyEntity.setQualification(facultyDto.getQualification());
			if (facultyDto.getStatus() != null)
				facultyEntity.setStatus(facultyDto.getStatus());
			if (facultyDto.getPassword() != null)
				facultyEntity.setPassword(encoder.encode(facultyDto.getPassword()));
			if (facultyDto.getRole() != null)
				facultyEntity.setRole(facultyDto.getRole());
			if (facultyDto.getDepartment() != null)
				facultyEntity.setDepartment(facultyDto.getDepartment());
			if (facultyDto.getAddress() != null)
				facultyEntity.setAddress(facultyDto.getAddress());
			if (facultyDto.getGender() != null)
				facultyEntity.setGender(facultyDto.getGender());

			// Upload profile picture if provided
			if (profilePicture != null && !profilePicture.isEmpty()) {
				String profilePictureUrl = cloudinaryService.uploadFileToDocumentsFolder(profilePicture,
						facultyDto.getName());
				facultyEntity.setProfilePicture(profilePictureUrl);
			}

			// Save the updated faculty details to the database
			facultyRepository.save(facultyEntity);

			return ResponseEntity.ok("Faculty profile updated successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Error updating faculty profile: " + e.getMessage());
		}
	}

	@PutMapping("/updateadmin")
	public ResponseEntity<?> updateAdmin(@RequestBody AdminDto adminDto) {
		try {
			// Find admin by ID
			Optional<AdminEntity> optional = adminRepository.findById(adminDto.getAdminId());
			if (optional.isEmpty()) {
				return ResponseEntity.badRequest().body("Admin not found with the provided ID.");
			}

			AdminEntity adminEntity = optional.get();

			// Update admin details
			adminEntity.setName(adminDto.getName());
			adminEntity.setEmail(adminDto.getEmail());
			adminEntity.setPhoneNumber(adminDto.getPhoneNumber());
			adminEntity.setQualification(adminDto.getQualification());
			adminEntity.setStatus(adminDto.getStatus());
			adminEntity.setPassword(encoder.encode(adminDto.getPassword()));

			// Set token if provided
			if (adminDto.getToken() != null && !adminDto.getToken().isEmpty()) {
				adminEntity.setToken(adminDto.getToken());
			}

			String profilePictureUrl = cloudinaryService.uploadFileToDocumentsFolder(adminDto.getProfilePicture(),
					adminDto.getName());
			adminEntity.setProfilePicture(profilePictureUrl);

			// Save the updated admin details to the database
			adminRepository.save(adminEntity);

			return ResponseEntity.ok("Admin profile updated successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Error updating admin profile: " + e.getMessage());
		}
	}
}
