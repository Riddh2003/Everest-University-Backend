package com.everestuniversity.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.everestuniversity.dto.AdminDto;
import com.everestuniversity.entity.AdminEntity;
import com.everestuniversity.entity.StudentEntity;
import com.everestuniversity.repository.AdminRepository;
import com.everestuniversity.repository.StudentRepository;
import com.everestuniversity.service.CloudinaryService;

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
	CloudinaryService cloudinaryService;

	@GetMapping("/getstudent")
	public ResponseEntity<?> getStudent(@RequestParam UUID Id) {
		Optional<StudentEntity> optional = studentRepository.findById(Id);
		if (optional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
		}
		StudentEntity student = optional.get();
		return ResponseEntity.status(HttpStatus.FOUND).body(student);
	}// end of method

	@GetMapping("/getallstudent")
	public ResponseEntity<?> getAllUser() {
		List<StudentEntity> userEntity = studentRepository.findAll();
		if (userEntity.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No users found...");
		}
		return ResponseEntity.ok(null);
	}

	@PutMapping("/updatestudent")
	public ResponseEntity<?> updateStudent(@ModelAttribute StudentEntity entity) {
		try {
			// Find student in the database by email
			Optional<StudentEntity> optional = studentRepository.findByEmail(entity.getEmail());
			if (optional.isEmpty()) {
				return ResponseEntity.badRequest().body("Student not found with the provided email.");
			}

			StudentEntity studentEntity = optional.get();

			// Update student details
			studentEntity.setSurName(entity.getSurName());
			studentEntity.setFirstName(entity.getFirstName());
			studentEntity.setMiddleName(entity.getMiddleName());
			studentEntity.setMobileNo(entity.getMobileNo());
			studentEntity.setEmail(entity.getEmail());
			studentEntity.setPassword(encoder.encode(entity.getPassword()));
			studentEntity.setGender(entity.getGender());
			studentEntity.setDateOfBirth(entity.getDateOfBirth());
			studentEntity.setProgram(entity.getProgram());
			studentEntity.setDegree(entity.getDegree());
			studentEntity.setDegreeName(entity.getDegreeName());
			studentEntity.setCurrentSem(entity.getCurrentSem());
			studentEntity.setCurrentYear(entity.getCurrentYear());

			// Save updated details to the database
			studentRepository.save(studentEntity);

			return ResponseEntity.ok("Student details updated successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Error: " + e.getMessage());
		}
	}

	@PutMapping("/updateadmin")
	public ResponseEntity<?> updateAdmin(@RequestParam UUID adminId, @RequestBody AdminDto adminDto) {
		try {
			// Find admin by ID
			Optional<AdminEntity> optional = adminRepository.findById(adminId);
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
