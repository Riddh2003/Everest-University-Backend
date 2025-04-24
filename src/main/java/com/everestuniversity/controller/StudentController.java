package com.everestuniversity.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.everestuniversity.entity.FeesEntity;
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

	// Fix the typo in the endpoint name from "getstudentprofie" to
	// "getstudentprofile"
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
	public ResponseEntity<?> updateStudentProfile(@RequestParam String enrollmentId,
			@RequestBody StudentProfileEntity newProfile) {
		HashMap<String, Object> response = new HashMap<>();
		if (studentService.getStudentProfile(enrollmentId) == null) {
			response.put("success", false);
			response.put("message", "StudentProfile not found");
			return ResponseEntity.ok(response);
		}
		StudentProfileEntity existingProfile = studentService.getStudentProfile(enrollmentId);

		StudentProfileEntity updatedProfile = studentService.updateProfile(existingProfile, newProfile);

		response.put("success", true);
		response.put("message", "Student profile updated successfully");
		response.put("data", updatedProfile);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/getfees/{enrollmentId}")
	public ResponseEntity<?> getStudentFees(@PathVariable String enrollmentId) {
		HashMap<String, Object> response = new HashMap<>();

		try {
			FeesEntity fees = studentService.getStudentFees(enrollmentId);

			if (fees == null) {
				response.put("success", false);
				response.put("message", "No fee records found for this student");
				return ResponseEntity.ok(response);
			}

			response.put("success", true);
			response.put("message", "Fee data retrieved successfully");
			response.put("data", fees);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Error retrieving fee data: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PostMapping("/updatefees")
	public ResponseEntity<?> updateStudentFees(@RequestBody FeesEntity fees) {
		HashMap<String, Object> response = new HashMap<>();

		try {
			// Check for enrollment ID in the request
			if (fees.getStudent() == null || fees.getStudent().getEnrollmentId() == null
					|| fees.getStudent().getEnrollmentId().isEmpty()) {
				response.put("success", false);
				response.put("message", "Enrollment ID is required");
				return ResponseEntity.badRequest().body(response);
			}

			String enrollmentId = fees.getStudent().getEnrollmentId();
			FeesEntity updatedFees = studentService.updateFees(enrollmentId, fees);

			response.put("success", true);
			response.put("message", "Fee data updated successfully");
			response.put("data", updatedFees);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Error updating fee data: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PostMapping("/updatefeesbyid")
	public ResponseEntity<?> updateStudentFeesByEnrollmentId(@RequestBody HashMap<String, Object> feeData) {
		HashMap<String, Object> response = new HashMap<>();

		try {
			// Extract enrollment ID from the request
			String enrollmentId = (String) feeData.get("enrollmentId");
			if (enrollmentId == null || enrollmentId.isEmpty()) {
				response.put("success", false);
				response.put("message", "Enrollment ID is required");
				return ResponseEntity.badRequest().body(response);
			}

			// Create a new FeesEntity from the request data
			FeesEntity fees = new FeesEntity();
			fees.setFee_status((String) feeData.get("fee_status"));

			// Convert string values to Long if needed
			try {
				if (feeData.get("total_fees") != null) {
					if (feeData.get("total_fees") instanceof Number) {
						fees.setTotal_fees(((Number) feeData.get("total_fees")).longValue());
					} else {
						fees.setTotal_fees(Long.parseLong(feeData.get("total_fees").toString()));
					}
				}

				if (feeData.get("paid_fees") != null) {
					if (feeData.get("paid_fees") instanceof Number) {
						fees.setPaid_fees(((Number) feeData.get("paid_fees")).longValue());
					} else {
						fees.setPaid_fees(Long.parseLong(feeData.get("paid_fees").toString()));
					}
				}

				if (feeData.get("due_fees") != null) {
					if (feeData.get("due_fees") instanceof Number) {
						fees.setDue_fees(((Number) feeData.get("due_fees")).longValue());
					} else {
						fees.setDue_fees(Long.parseLong(feeData.get("due_fees").toString()));
					}
				}
			} catch (NumberFormatException e) {
				response.put("success", false);
				response.put("message", "Invalid number format for fees values");
				return ResponseEntity.badRequest().body(response);
			}

			fees.setPayment_mode((String) feeData.get("payment_mode"));

			// Update fees using the service
			FeesEntity updatedFees = studentService.updateFees(enrollmentId, fees);

			response.put("success", true);
			response.put("message", "Fee data updated successfully");
			response.put("data", updatedFees);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Error updating fee data: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
}
