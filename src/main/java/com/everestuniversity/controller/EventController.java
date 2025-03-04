package com.everestuniversity.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.everestuniversity.dto.EventDto;
import com.everestuniversity.dto.EventRegistrationDto;
import com.everestuniversity.entity.EventEntity;
import com.everestuniversity.entity.EventRegistrationEntity;
import com.everestuniversity.repository.EventRegistrationRepository;
import com.everestuniversity.repository.EventRepository;

@RestController
@RequestMapping("/api/private/events/")
public class EventController {

	@Autowired
	EventRepository eventRepository;

	@Autowired
	EventRegistrationRepository eventRegistrationRepository;

	// create new evenet method
	@PostMapping("/createnewevent")
	public ResponseEntity<?> createNewEvent(@RequestBody EventDto eventDto) {
		EventEntity eventEntity = new EventEntity();
		eventEntity.setName(eventDto.getName());
		eventEntity.setDescription(eventDto.getDescription());
		eventEntity.setStartDate(eventEntity.getStartDate());
		eventEntity.setEndDate(eventEntity.getEndDate());
		eventEntity.setLocation(eventEntity.getLocation());

		EventEntity event = eventRepository.save(eventEntity);
		return ResponseEntity.ok(eventEntity);
	}

	@GetMapping("/getallevents")
	public ResponseEntity<?> getAllEvents() {
		List<EventEntity> events = eventRepository.findAll();
		if (events.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Right now, No events available.");
		}
		return ResponseEntity.ok(events);
	}

	@GetMapping("/geteventbyid")
	public ResponseEntity<?> getEventById(@RequestParam("eventId") UUID eventId) {
		Optional<EventEntity> optional = eventRepository.findById(eventId);
		EventEntity event = optional.get();
		if (event.equals(null)) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Events no available");
		}
		return ResponseEntity.ok(event);
	}

	@PostMapping("/registerevent")
	public ResponseEntity<?> registerEvent(@RequestParam UUID eventId,
			@RequestBody EventRegistrationDto eventRegistrationDto) {

		return ResponseEntity.ok(null);
	}

	@GetMapping("/getalleventregistation")
	public ResponseEntity<?> getAllEventRegistration() {
		List<EventRegistrationEntity> register = eventRegistrationRepository.findAll();
		if (register.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No, registerations are register yet!");
		}
		return ResponseEntity.ok(register);
	}

}
