package com.everestuniversity.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.everestuniversity.dto.AdminDto;

import com.everestuniversity.entity.AdminEntity;
import com.everestuniversity.entity.FacultyEntity;

import com.everestuniversity.repository.AdminRepository;
import com.everestuniversity.repository.FacultyRepository;
import com.everestuniversity.repository.StudentRepository;
import com.everestuniversity.service.CloudinaryService;
import com.everestuniversity.service.JwtService;

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
