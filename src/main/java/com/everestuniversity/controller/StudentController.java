package com.everestuniversity.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.everestuniversity.entity.StudentEntity;
import com.everestuniversity.entity.StudentProfileEntity;
import com.everestuniversity.repository.StudentProfileRepository;
import com.everestuniversity.repository.StudentRepository;
import com.everestuniversity.service.StudentService;

@RestController
@RequestMapping("/api/private/student")
public class StudentController {

	@Autowired
	private StudentRepository studentRepo;

	@Autowired
	private StudentProfileRepository studentProfileRepo;

	@Autowired
	private StudentService studentService;

	@GetMapping("/getallstudent")
	public ResponseEntity<?> getAllStudent() {
		HashMap<String, Object> response = new HashMap<>();
		List<StudentEntity> students = studentRepo.findAll();
		response.put("success", true);
		response.put("message", "Students fetched successfully");
		response.put("data", students);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/getstudentbyid")
	public ResponseEntity<?> getStudentById(@RequestParam("studentId") String studentId) {
		HashMap<String, Object> response = new HashMap<>();
		StudentEntity student = studentService.getStudentById(studentId);
		if (student != null) {
			response.put("success", true);
			response.put("message", "Student fetched successfully");
			response.put("data", student);
			return ResponseEntity.ok(response);
		}
		response.put("success", false);
		response.put("message", "Student not found");
		return ResponseEntity.ok(response);
	}

	@PutMapping("/updatestudent")
	public ResponseEntity<?> updateStudent(@RequestParam("studentId") String studentID,
			@RequestPart(value = "file", required = false) MultipartFile file,
			@RequestPart(value = "student", required = false) String studentJson) {
		return null;
	}

	// ---------------------------------------------------------------------------------------------------

	// Student Profile handling start here

	@GetMapping("/getstudentprofie")
	public ResponseEntity<?> getStudentProfile(@RequestParam String studentId) {
		HashMap<String, Object> response = new HashMap<>();
		if (studentService.getStudentProfile(studentId) == null) {
			response.put("success", false);
			response.put("message", "StudentProfile not found");
			return ResponseEntity.ok(response);
		}

		StudentProfileEntity profile = studentService.getStudentProfile(studentId);
		response.put("success", true);
		response.put("message", "Studen Profile fetched successfully");
		response.put("data", profile);
		return ResponseEntity.ok(response);
	}

	@PutMapping("updateprofile")
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
