package com.everestuniversity.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.everestuniversity.dto.QueryDto;
import com.everestuniversity.entity.QueryEntity;
import com.everestuniversity.repository.QueryRepository;

@RestController
@RequestMapping("/api/public/queries/")
public class QueryController {

	@Autowired
	QueryRepository queryRepository;

	@Autowired
	JavaMailSender mailSender;

	// This API is used to submit a student query about Admission
	@PostMapping("/newcreate")
	public ResponseEntity<?> newQuery(@RequestParam UUID studentId, @RequestBody QueryDto queryDto) {
		HashMap<String, Object> response = new HashMap<>();
		try {
			Optional<QueryEntity> op = queryRepository.findById(studentId);
			if (op.isPresent()) {
				response.put("status", "A query with the same user is already exists.");
				return ResponseEntity.badRequest().body(response);
			}
			QueryEntity queryEntity = op.get();
			queryEntity.setStatus(queryDto.getStatus());
			queryRepository.save(queryEntity);

			response.put("status", "Thank you for your query, " + queryEntity.getTitle()
					+ "! Our admin team will review it shortly. Your query id is : " + queryEntity.getQueryId());

			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("riddhmodi2003@gmail.com");
			message.setTo(queryEntity.getStudent().getEmail());
			message.setSubject("Query Submitted Successfully");
			message.setText("Hi " + queryEntity.getTitle() + "\nThank you for your query, " + queryEntity.getTitle()
					+ "! Our admin team will review it shortly. Your query id is " + queryEntity.getQueryId()
					+ ". We will get back to you within 24 hours.");

			mailSender.send(message);

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			response.put("status", "An error occurred while submitting the query.");
			return ResponseEntity.badRequest().body(response);
		}
	} // END OF SUBMIT-QUERY METHOD

	// This API is used to check a query
	@GetMapping("/checkquery")
	public ResponseEntity<?> checkQuery(@RequestParam UUID studentId) {
		HashMap<String, Object> response = new HashMap<>();
		try {
			Optional<QueryEntity> op = queryRepository.findById(studentId);
			if (!op.isPresent()) {
				response.put("status", "No query found with the given userId.");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}

			QueryEntity queryEntity = op.get();

			if (queryEntity.getStatus().equalsIgnoreCase("pending")) {
				response.put("message", "Your query status is " + queryEntity.getStatus()
						+ ". Please wait for our admin team to review it.");
			} else if (queryEntity.getStatus().equalsIgnoreCase("Resolved")) {
				response.put("message",
						"Your query status is " + queryEntity.getStatus() + ". We have resolved your query.");
			} else if (queryEntity.getStatus().equalsIgnoreCase("in_process")) {
				response.put("message",
						"Your query status is " + queryEntity.getStatus() + ". We have inprogress your query.");
			} else if (queryEntity.getStatus().equalsIgnoreCase("rejected")) {
				response.put("message",
						"Your query status is " + queryEntity.getStatus() + ". We have rejected your query.");
			}

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			response.put("status", "An error occurred while checking the query.");
			return ResponseEntity.badRequest().body(response);
		}
	} // END OF CHECK-QUERY METHOD

	// Admin can access it only
	@GetMapping("/searchquery")
	public ResponseEntity<?> searchQuery(@RequestParam String prefix) {
		if (prefix == null || prefix.length() < 1) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Queries not found.");
		}
		Optional<List<QueryEntity>> optional = queryRepository.findByQueryName(prefix);
		List<QueryEntity> queryEntities = optional.get();
		return ResponseEntity.ok(queryEntities);
	}

	// this method only call by the admin and we call it deletequery method
	@PutMapping("/staffresponse")
	public ResponseEntity<?> updateQuery(@RequestParam UUID studentId, @RequestBody QueryDto queryDto) {
		HashMap<String, Object> response = new HashMap<>();
		try {
			Optional<QueryEntity> optional = queryRepository.findById(studentId);
			QueryEntity queryEntity = optional.get();
			queryEntity.setStatus(queryDto.getStatus());
			queryRepository.save(queryEntity);
			if (queryEntity.getStatus().equalsIgnoreCase("solved")
					|| queryEntity.getStatus().equalsIgnoreCase("rejected")
					|| queryEntity.getStatus().equalsIgnoreCase("in_process")) {

				response.put("status", "Thank you for your query, " + queryEntity.getTitle()
						+ "! Our admin team reviewed it. Your query is : " + queryEntity.getStatus());

				SimpleMailMessage message = new SimpleMailMessage();
				message.setFrom("riddhmodi2003@gmail.com");
				message.setTo(queryEntity.getStudent().getEmail());
				message.setSubject("Admin response for the query.");
				message.setText("Hi " + queryEntity.getTitle() + "\nThank you for your query, " + queryEntity.getTitle()
						+ "! Our admin team will review it. Your query id is " + queryEntity.getQueryId() + "We have "
						+ queryEntity.getStatus() + " your problem.");

				mailSender.send(message);

			}
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			response.put("status", "An error occurred while updating the query.");
			return ResponseEntity.badRequest().body(response);
		}
	}

}
