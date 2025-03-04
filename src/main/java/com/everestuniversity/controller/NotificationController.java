package com.everestuniversity.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.everestuniversity.dto.NotificationDto;
import com.everestuniversity.entity.AdminEntity;
import com.everestuniversity.entity.NotificationEntity;
import com.everestuniversity.entity.StudentEntity;
import com.everestuniversity.repository.AdminRepository;
import com.everestuniversity.repository.NotificationRepository;
import com.everestuniversity.repository.StudentRepository;

@RestController
@RequestMapping("/api/private/notifications/")
public class NotificationController {

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	AdminRepository adminRepository;

	@Autowired
	NotificationRepository notificationRepository;

	// Only, Admin can create the notification method
	@PostMapping("/createnotification")
	public ResponseEntity<?> createNotification(@RequestParam UUID adminId, @RequestParam UUID studentId,
			@RequestBody NotificationDto notificationDto) {
		Optional<AdminEntity> adminOptional = adminRepository.findById(adminId);
		Optional<StudentEntity> studentOptional = studentRepository.findById(studentId);
		if (adminOptional.isEmpty() & studentOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Amdin not found...");
		}
		AdminEntity admin = adminOptional.get();
		StudentEntity student = studentOptional.get();
		NotificationEntity notificationEntity = new NotificationEntity();
		notificationEntity.setMessage(notificationDto.getMessage());
		notificationEntity.setNotificationType(notificationDto.getNotificationType());
		notificationEntity.setAdmin(admin);
		notificationEntity.setStudent(student);

		// save the notification
		notificationRepository.save(notificationEntity);
		return ResponseEntity.ok("Notification sent to the webpage.");
	}

	// get notification in student portal
	// @GetMapping
	// public ResponseEntity<?> getNotification(@RequestParam UUID studentId) {
	// Optional<List<NotificationEntity>> optional =
	// notificationRepository.find(studentId);
	// if (optional.isEmpty()) {
	// return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Notification not
	// found...");
	// }
	// List<NotificationEntity> notifications = optional.get();
	// return ResponseEntity.ok("Notifications are available" + notifications);
	// }

	// Student can delete the notification
	@DeleteMapping("/deletenotification")
	public ResponseEntity<?> deleteNotification(@RequestParam UUID notificationId) {
		Optional<NotificationEntity> optional = notificationRepository.findById(notificationId);
		NotificationEntity notificationEntity = optional.get();
		notificationRepository.delete(notificationEntity);
		return ResponseEntity.ok("Notification succesfully deleted.");
	}

}
