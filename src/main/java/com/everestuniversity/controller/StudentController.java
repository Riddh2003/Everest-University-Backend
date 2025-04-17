package com.everestuniversity.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.everestuniversity.entity.StudentEntity;
import com.everestuniversity.entity.StudentProfileEntity;
import com.everestuniversity.repository.StudentRepository;
import com.everestuniversity.service.StudentService;

@RestController
@RequestMapping("/api/private/student")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@Autowired
	StudentRepository studentRepository;

	@GetMapping("/getstudent")
	public ResponseEntity<?> getStudent(@RequestParam String enrollmentId) {
	    // Find student by enrollment ID
	    Optional<StudentEntity> optional = studentRepository.findById(enrollmentId);
	    if (optional.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
	    }
	    StudentEntity student = optional.get();
	    System.out.println("Student found: " + student.getEnrollmentId());
	    return ResponseEntity.ok(student);
	}

	@GetMapping("/getallstudent")
	public ResponseEntity<?> getAllUser() {
		List<StudentEntity> students = studentRepository.findAll();
		if (students.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No users found...");
		}
		return ResponseEntity.ok(students);
	}


	// Fix the typo in the endpoint name from "getstudentprofie" to "getstudentprofile"
	@GetMapping("/getstudentprofile")
	public ResponseEntity<?> getStudentProfile(@RequestParam String enrollmentId) {
		HashMap<String, Object> response = new HashMap<>();
		if (studentService.getStudentProfile(enrollmentId) == null) {
			response.put("success", false);
			response.put("message", "StudentProfile not found");
			return ResponseEntity.ok(response);
		}

		StudentProfileEntity profile = studentService.getStudentProfile(enrollmentId);
		response.put("success", true);
		response.put("message", "Student Profile fetched successfully");
		response.put("data", profile);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/updateprofile")
	public ResponseEntity<?> updateStudentProfile(@RequestParam String profileId,
			@RequestBody StudentProfileEntity newProfile) {
		HashMap<String, Object> response = new HashMap<>();
		if (studentService.getStudentById(profileId) == null) {
			response.put("success", false);
			response.put("message", "StudentProfile not found");
			return ResponseEntity.ok(response);
		}
		StudentProfileEntity existingProfile = studentService.getStudentProfile(profileId);

		StudentProfileEntity updatedProfile = studentService.updateProfile(existingProfile, newProfile);

		return ResponseEntity.ok(response);
	}
}
